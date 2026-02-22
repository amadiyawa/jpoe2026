package com.amadiyawa.feature_personnality.presentation.screen.userinfo

import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import com.amadiyawa.feature_base.domain.result.OperationResult
import com.amadiyawa.feature_base.presentation.screen.viewmodel.BaseState
import com.amadiyawa.feature_base.presentation.screen.viewmodel.BaseViewModel
import com.amadiyawa.feature_personnality.data.datasource.database.MbtiDescriptionLocalDataSource
import com.amadiyawa.feature_personnality.domain.model.MbtiResult
import com.amadiyawa.feature_personnality.domain.model.UserInfo
import com.amadiyawa.feature_personnality.domain.model.UserSituation
import com.amadiyawa.feature_personnality.domain.usecase.GetAiDescriptionUseCase
import com.amadiyawa.feature_personnality.domain.usecase.SaveMbtiResultUseCase
import kotlinx.coroutines.launch
import timber.log.Timber

internal class UserInfoViewModel(
    private val getAiDescriptionUseCase: GetAiDescriptionUseCase,
    private val saveMbtiResultUseCase: SaveMbtiResultUseCase,
    private val mbtiDescriptionLocalDataSource: MbtiDescriptionLocalDataSource
) : BaseViewModel<UserInfoViewModel.UiState, UserInfoViewModel.Action>(UiState.Form()) {

    override fun dispatch(action: Action) {
        logAction(action)
        when (action) {
            is Action.FirstNameChanged -> setState {
                if (it is UiState.Form) it.copy(firstName = action.value) else it
            }
            is Action.AgeChanged -> setState {
                if (it is UiState.Form) it.copy(age = action.value) else it
            }
            is Action.CityChanged -> setState {
                if (it is UiState.Form) it.copy(city = action.value) else it
            }
            is Action.SituationChanged -> setState {
                if (it is UiState.Form) it.copy(situation = action.value) else it
            }
            is Action.Submit -> submitForm(action.mbtiType)
        }
    }

    // ─── LOGIQUE ──────────────────────────────────────────────────────────────

    private fun submitForm(mbtiType: String) {
        val currentState = state
        if (currentState !is UiState.Form) return
        if (!currentState.isValid) return

        setState { UiState.Loading }

        viewModelScope.launch {
            val userInfo = UserInfo(
                firstName = currentState.firstName.trim(),
                age = currentState.age.trim().toIntOrNull() ?: 0,
                city = currentState.city.trim(),
                situation = currentState.situation
            )

            // Tentative appel API Gemini
            val aiDescription: String? = when (val result = getAiDescriptionUseCase(mbtiType, userInfo)) {
                is OperationResult.Success -> {
                    Timber.d("Description IA reçue pour $mbtiType")
                    result.data  // ← IA disponible
                }
                else -> {
                    Timber.w("API indisponible → fallback description statique")
                    null  // ← null = pas d'IA
                }
            }

            // Description statique toujours chargée (affichée si IA indisponible)
            val staticDescription = mbtiDescriptionLocalDataSource
                .getFormattedDescription(mbtiType)

            // Sauvegarde du résultat
            val mbtiResult = MbtiResult(
                userInfo = userInfo,
                mbtiType = mbtiType,
                aiDescription = aiDescription,
                staticDescription = staticDescription
            )

            saveMbtiResultUseCase(mbtiResult)
            Timber.d("Résultat sauvegardé : $mbtiType")

            setState { UiState.Success(mbtiType) }
        }
    }

    // ─── ACTIONS ──────────────────────────────────────────────────────────────

    sealed class Action {
        data class FirstNameChanged(val value: String) : Action()
        data class AgeChanged(val value: String) : Action()
        data class CityChanged(val value: String) : Action()
        data class SituationChanged(val value: UserSituation) : Action()
        data class Submit(val mbtiType: String) : Action()
    }

    // ─── ÉTATS ────────────────────────────────────────────────────────────────

    @Immutable
    sealed interface UiState : BaseState {

        // Formulaire en cours de saisie
        data class Form(
            val firstName: String = "",
            val age: String = "",
            val city: String = "",
            val situation: UserSituation = UserSituation.STUDENT
        ) : UiState {
            // Validation : tous les champs obligatoires remplis
            val isValid: Boolean
                get() = firstName.isNotBlank()
                        && age.isNotBlank()
                        && age.toIntOrNull() != null
                        && age.toInt() in 10..80
                        && city.isNotBlank()
        }

        // Soumission en cours (appel API + sauvegarde)
        data object Loading : UiState

        // Succès → navigation vers ResultScreen
        data class Success(val mbtiType: String) : UiState

        // Erreur inattendue
        data object Error : UiState
    }
}