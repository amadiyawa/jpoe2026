package com.amadiyawa.feature_personnality.presentation.screen.history

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

internal class HistoryViewModel(
    private val getAllResultsUseCase: GetAllResultsUseCase
) : BaseViewModel<HistoryViewModel.UiState, HistoryViewModel.Action>(UiState.Loading) {

    private var job: Job? = null

    override fun dispatch(action: Action) {
        logAction(action)
        when (action) {
            is Action.LoadHistory -> loadHistory()
        }
    }

    fun onEnter() {
        dispatch(Action.LoadHistory)
    }

    // ─── LOGIQUE ──────────────────────────────────────────────────────────────

    private fun loadHistory() {
        job?.cancel()
        job = viewModelScope.launch {
            setState { UiState.Loading }

            getAllResultsUseCase().also { result ->
                Timber.d("getAllResultsUseCase result: $result")
                setState {
                    when (result) {
                        is OperationResult.Success -> {
                            if (result.data.isEmpty()) {
                                UiState.Empty
                            } else {
                                UiState.History(resultList = result.data)
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
        data object LoadHistory : Action()
    }

    // ─── ÉTATS ────────────────────────────────────────────────────────────────

    @Immutable
    sealed interface UiState : BaseState {

        // Chargement
        data object Loading : UiState

        // Aucun résultat encore
        data object Empty : UiState

        // Erreur de chargement
        data object Error : UiState

        // Liste des résultats (du plus récent au plus ancien)
        data class History(
            val resultList: List<MbtiResult>
        ) : UiState
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}