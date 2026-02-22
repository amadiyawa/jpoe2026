package com.amadiyawa.feature_personnality.presentation.screen.questionnaire

import androidx.compose.runtime.Immutable
import androidx.lifecycle.viewModelScope
import com.amadiyawa.feature_base.domain.result.OperationResult
import com.amadiyawa.feature_base.presentation.screen.viewmodel.BaseState
import com.amadiyawa.feature_base.presentation.screen.viewmodel.BaseViewModel
import com.amadiyawa.feature_personnality.domain.model.Question
import com.amadiyawa.feature_personnality.domain.usecase.CalculateMbtiTypeUseCase
import com.amadiyawa.feature_personnality.domain.usecase.GetQuestionsUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

internal class QuestionnaireViewModel(
    private val getQuestionsUseCase: GetQuestionsUseCase,
    private val calculateMbtiTypeUseCase: CalculateMbtiTypeUseCase
) : BaseViewModel<QuestionnaireViewModel.UiState, QuestionnaireViewModel.Action>(UiState.Loading) {

    private var job: Job? = null

    // Stocke les réponses : Map<questionId, "A" ou "B">
    private val _answers = MutableStateFlow<Map<Int, String>>(emptyMap())

    // Option sélectionnée sur la question courante
    private val _currentSelectedOption = MutableStateFlow("")
    val currentSelectedOption = _currentSelectedOption.asStateFlow()

    // ─── DISPATCH ─────────────────────────────────────────────────────────────

    override fun dispatch(action: Action) {
        logAction(action)
        when (action) {
            is Action.LoadQuestions -> loadQuestions()
            is Action.SelectOption -> onOptionSelected(action.option)
            is Action.NextQuestion -> goToNextQuestion()
            is Action.PreviousQuestion -> goToPreviousQuestion()
        }
    }

    fun onEnter() {
        dispatch(Action.LoadQuestions)
    }

    // ─── LOGIQUE ──────────────────────────────────────────────────────────────

    private fun loadQuestions() {
        job?.cancel()
        job = viewModelScope.launch {
            setState { UiState.Loading }

            getQuestionsUseCase().also { result ->
                Timber.d("getQuestionsUseCase result: $result")
                setState {
                    when (result) {
                        is OperationResult.Success -> {
                            if (result.data.isEmpty()) UiState.Error
                            else UiState.Questionnaire(questionList = result.data)
                        }
                        is OperationResult.Error -> UiState.Error
                        is OperationResult.Failure -> UiState.Error
                    }
                }
            }
        }
    }

    private fun onOptionSelected(option: String) {
        _currentSelectedOption.value = option
    }

    private fun goToNextQuestion() {
        val currentState = state
        if (currentState !is UiState.Questionnaire) return

        val selectedOption = _currentSelectedOption.value
        if (selectedOption.isBlank()) return

        // Sauvegarde la réponse
        val question = currentState.currentQuestion
        _answers.value = _answers.value.toMutableMap().apply {
            put(question.id, selectedOption)
        }

        Timber.d("Réponse Q${question.id} : $selectedOption")

        if (currentState.isLastQuestion) {
            // Toutes les questions répondues → calcul MBTI
            val mbtiType = calculateMbtiTypeUseCase(
                answers = _answers.value,
                questions = currentState.questionList
            )
            Timber.d("Type MBTI calculé : $mbtiType")
            setState { UiState.Complete(mbtiType) }
        } else {
            // Question suivante
            setState { it ->
                if (it is UiState.Questionnaire) {
                    it.copy(currentQuestionIndex = it.currentQuestionIndex + 1)
                } else it
            }
            _currentSelectedOption.value = ""
        }
    }

    private fun goToPreviousQuestion() {
        val currentState = state
        if (currentState !is UiState.Questionnaire) return
        if (currentState.isFirstQuestion) return

        val prevIndex = currentState.currentQuestionIndex - 1

        // Restaure la réponse précédente si elle existe
        val prevQuestion = currentState.questionList[prevIndex]
        _currentSelectedOption.value = _answers.value[prevQuestion.id] ?: ""

        setState { it ->
            if (it is UiState.Questionnaire) {
                it.copy(currentQuestionIndex = prevIndex)
            } else it
        }
    }

    // ─── ACTIONS ──────────────────────────────────────────────────────────────

    sealed class Action {
        data object LoadQuestions : Action()
        data class SelectOption(val option: String) : Action()
        data object NextQuestion : Action()
        data object PreviousQuestion : Action()
    }

    // ─── ÉTATS ────────────────────────────────────────────────────────────────

    @Immutable
    sealed interface UiState : BaseState {

        data object Loading : UiState
        data object Error : UiState

        data class Questionnaire(
            val questionList: List<Question>,
            val currentQuestionIndex: Int = 0
        ) : UiState {
            val currentQuestion: Question
                get() = questionList[currentQuestionIndex]

            val isFirstQuestion: Boolean
                get() = currentQuestionIndex == 0

            val isLastQuestion: Boolean
                get() = currentQuestionIndex == questionList.size - 1

            val progress: String
                get() = "${currentQuestionIndex + 1} / ${questionList.size}"

            val progressPercent: Float
                get() = (currentQuestionIndex + 1).toFloat() / questionList.size.toFloat()
        }

        data class Complete(val mbtiType: String) : UiState
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}