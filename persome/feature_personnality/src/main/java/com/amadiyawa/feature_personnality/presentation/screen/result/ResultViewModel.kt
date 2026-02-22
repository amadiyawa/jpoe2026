package com.amadiyawa.feature_personnality.presentation.screen.result

import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import com.amadiyawa.feature_base.domain.result.OperationResult
import com.amadiyawa.feature_base.presentation.screen.viewmodel.BaseState
import com.amadiyawa.feature_base.presentation.screen.viewmodel.BaseViewModel
import com.amadiyawa.feature_personnality.domain.model.MbtiResult
import com.amadiyawa.feature_personnality.domain.usecase.GetAllResultsUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

internal class ResultViewModel(
    private val getAllResultsUseCase: GetAllResultsUseCase
) : BaseViewModel<ResultViewModel.UiState, ResultViewModel.Action>(UiState.Loading) {

    private var job: Job? = null

    override fun dispatch(action: Action) {
        logAction(action)
        when (action) {
            is Action.LoadResult -> loadResult(action.mbtiType)
        }
    }

    fun onEnter(mbtiType: String) {
        dispatch(Action.LoadResult(mbtiType))
    }

    // ─── LOGIQUE ──────────────────────────────────────────────────────────────

    private fun loadResult(mbtiType: String) {
        job?.cancel()
        job = viewModelScope.launch {
            setState { UiState.Loading }

            // Récupère le dernier résultat correspondant au type
            getAllResultsUseCase().also { result ->
                Timber.d("loadResult for mbtiType: $mbtiType")
                setState {
                    when (result) {
                        is OperationResult.Success -> {
                            // Cherche le résultat le plus récent pour ce type
                            val mbtiResult = result.data.firstOrNull {
                                it.mbtiType == mbtiType
                            }
                            if (mbtiResult != null) {
                                UiState.Result(mbtiResult)
                            } else {
                                UiState.Error
                            }
                        }
                        is OperationResult.Error -> UiState.Error
                        is OperationResult.Failure -> UiState.Error
                    }
                }
            }
        }
    }

    // ─── ACTIONS ──────────────────────────────────────────────────────────────

    sealed class Action {
        data class LoadResult(val mbtiType: String) : Action()
    }

    // ─── ÉTATS ────────────────────────────────────────────────────────────────

    @Immutable
    sealed interface UiState : BaseState {
        data object Loading : UiState
        data object Error : UiState
        data class Result(val mbtiResult: MbtiResult) : UiState
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}