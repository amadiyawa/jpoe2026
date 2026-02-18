package com.amadiyawa.feature_onboarding.presentation.screen.onboarding

import androidx.compose.runtime.mutableStateOf
import com.amadiyawa.feature_base.data.datastore.DataStoreManager
import com.amadiyawa.feature_base.domain.result.OperationResult
import com.amadiyawa.feature_base.presentation.screen.viewmodel.BaseViewModel
import com.amadiyawa.feature_onboarding.domain.usecase.GetOnboardingUseCase
import kotlinx.coroutines.flow.first
import timber.log.Timber

internal class OnboardingViewModel(
    private val getOnboardingUseCase: GetOnboardingUseCase,
    private val dataStoreManager: DataStoreManager
) : BaseViewModel<OnboardingUiState, OnboardingAction>(OnboardingUiState()) {

    private val _permissionsState = mutableStateOf<Map<String, Boolean>>(emptyMap())
    val permissionsState: Map<String, Boolean> get() = _permissionsState.value

    private val _permissionsGranted = mutableStateOf(false)
    val permissionsGranted: Boolean get() = _permissionsGranted.value

    init {
        launchSafely {
            val isOnboardingComplete = dataStoreManager
                .getData(DataStoreManager.ONBOARDING_COMPLETED)
                .first() == true

            if (isOnboardingComplete) {
                emitEvent(OnboardingUiEvent.NavigateToAuth)
            } else {
                dispatch(OnboardingAction.LoadScreens)
            }
        }
    }

    override fun dispatch(action: OnboardingAction) {
        logAction(action)
        when (action) {
            is OnboardingAction.LoadScreens -> loadScreens()
            is OnboardingAction.NextScreen -> goToNextScreen()
            is OnboardingAction.PreviousScreen -> goToPreviousScreen()
            is OnboardingAction.CompleteOnboarding -> completeOnboarding()
            is OnboardingAction.GoToScreen -> goToScreen(action.index)
            is OnboardingAction.RequestPermissions -> emitPermissionsRequest()
            is OnboardingAction.OpenSettings -> emitOpenSettings()
        }
    }

    private fun loadScreens() {
        launchSafely {
            setState { it.copy(isLoading = true, error = null) }

            getOnboardingUseCase().also { result ->
                Timber.d("getOnboardListUseCase result: $result")

                setState { state ->
                    when (result) {
                        is OperationResult.Success -> {
                            val permissionsScreen = result.data.find { it.requiresPermissions }
                            if (permissionsScreen != null) {
                                _permissionsState.value = permissionsScreen.permissions
                                    .associateWith { false }
                            }

                            state.copy(
                                screens = result.data,
                                isLoading = false,
                                error = null
                            )
                        }
                        is OperationResult.Error -> {
                            emitEvent(OnboardingUiEvent.ShowError(result.message!!))
                            state.copy(isLoading = false, error = result.message!!)
                        }
                        is OperationResult.Failure -> state
                    }
                }
            }
        }
    }

    private fun goToNextScreen() {
        val currentState = state
        val screen = currentState.currentScreen

        if (screen?.requiresPermissions == true && !permissionsGranted) {
            Timber.w("Cannot proceed without all permissions")
        } else if (currentState.isLastScreen) {
            completeOnboarding()
        } else {
            setState { it.copy(currentScreenIndex = it.currentScreenIndex + 1) }
        }
    }

    private fun goToPreviousScreen() {
        setState { currentState ->
            if (!currentState.isFirstScreen) {
                currentState.copy(currentScreenIndex = currentState.currentScreenIndex - 1)
            } else {
                currentState
            }
        }
    }

    private fun goToScreen(index: Int) {
        setState { currentState ->
            if (index in 0 until currentState.screens.size) {
                currentState.copy(currentScreenIndex = index)
            } else {
                Timber.w("Attempted to navigate to invalid screen index: $index")
                currentState
            }
        }
    }

    private fun completeOnboarding() {
        launchSafely  {
            dataStoreManager.saveData(DataStoreManager.ONBOARDING_COMPLETED, true)
            emitEvent(OnboardingUiEvent.NavigateToAuth)
        }
    }

    private fun emitPermissionsRequest() {
        val permissions = state.currentScreen
            ?.takeIf { it.requiresPermissions }
            ?.permissions
            ?.filter { _permissionsState.value[it] != true }
            .orEmpty()

        if (permissions.isNotEmpty()) {
            Timber.d("Requesting permissions: ${permissions.joinToString()}")
            emitEvent(OnboardingUiEvent.RequestPermissions(permissions))
        } else {
            Timber.d("All permissions already granted")
            dispatch(OnboardingAction.NextScreen)
        }
    }

    private fun emitOpenSettings() {
        emitEvent(OnboardingUiEvent.OpenSettings)
    }

    fun handlePermissionsResult(
        results: Map<String, Boolean>,
        deniedPermissions: List<String>
    ) {
        Timber.d("Permissions result: $results")
        Timber.d("Denied permissions: ${deniedPermissions.joinToString()}")

        _permissionsState.value = _permissionsState.value.toMutableMap().apply {
            results.forEach { (permission, granted) ->
                this[permission] = granted
            }
        }

        val allGranted = _permissionsState.value.values.all { it }
        _permissionsGranted.value = allGranted

        if (allGranted) {
            Timber.d("All permissions granted")
            dispatch(OnboardingAction.NextScreen)
        } else {
            if (deniedPermissions.isNotEmpty()) {
                emitEvent(OnboardingUiEvent.ShowPermissionsBlocked(deniedPermissions))
            } else {
                Timber.w("Some permissions still denied")
            }
        }
    }

    fun syncPermissionsState(permissionsState: Map<String, Boolean>) {
        _permissionsState.value = permissionsState
        _permissionsGranted.value = permissionsState.values.all { it }
        Timber.d("Synced permissions state: $permissionsState")
    }

    fun canProceed(): Boolean {
        val screen = state.currentScreen
        return if (screen?.requiresPermissions == true) {
            permissionsGranted
        } else {
            true
        }
    }
}