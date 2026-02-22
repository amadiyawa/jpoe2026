package com.amadiyawa.feature_personnality.presentation.screen.questionnaire

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amadiyawa.feature_base.presentation.compose.composable.ButtonIconType
import com.amadiyawa.feature_base.presentation.compose.composable.CircularButton
import com.amadiyawa.feature_base.presentation.compose.composable.CircularButtonParams
import com.amadiyawa.feature_base.presentation.compose.composable.FilledButton
import com.amadiyawa.feature_base.presentation.compose.composable.LoadingAnimation
import com.amadiyawa.feature_base.presentation.compose.composable.TextBodyMedium
import com.amadiyawa.feature_base.presentation.compose.composable.TextHeadlineLarge
import com.amadiyawa.feature_base.presentation.compose.composable.TextTitleMedium
import com.amadiyawa.feature_base.presentation.theme.dimension
import com.amadiyawa.feature_personnality.R
import com.amadiyawa.feature_personnality.domain.model.Question
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun QuestionnaireScreen(
    onQuestionnaireComplete: (String) -> Unit,
    onBackClick: () -> Unit
) {
    val viewModel: QuestionnaireViewModel = koinViewModel()

    LaunchedEffect(Unit) {
        viewModel.onEnter()
    }

    val uiState = viewModel.uiStateFlow.collectAsStateWithLifecycle().value

    // Observe l'état Complete → navigation vers UserInfoScreen
    LaunchedEffect(uiState) {
        if (uiState is QuestionnaireViewModel.UiState.Complete) {
            onQuestionnaireComplete(uiState.mbtiType)
        }
    }

    Scaffold(
        contentColor = MaterialTheme.colorScheme.onBackground,
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { paddingValues ->
        HandleUiState(
            uiState = uiState,
            paddingValues = paddingValues,
            viewModel = viewModel,
            onBackClick = onBackClick
        )
    }
}

// ─── ÉTATS ────────────────────────────────────────────────────────────────────

@Composable
private fun HandleUiState(
    uiState: QuestionnaireViewModel.UiState,
    paddingValues: PaddingValues,
    viewModel: QuestionnaireViewModel,
    onBackClick: () -> Unit
) {
    when (uiState) {
        is QuestionnaireViewModel.UiState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                LoadingAnimation(visible = true)
            }
        }

        is QuestionnaireViewModel.UiState.Error -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.error_loading_questions),
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        is QuestionnaireViewModel.UiState.Questionnaire -> {
            QuestionnaireContent(
                uiState = uiState,
                paddingValues = paddingValues,
                viewModel = viewModel,
                onBackClick = onBackClick
            )
        }

        is QuestionnaireViewModel.UiState.Complete -> {
            // Navigation gérée par LaunchedEffect → affiche loading
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                LoadingAnimation(visible = true)
            }
        }
    }
}

// ─── CONTENU PRINCIPAL ────────────────────────────────────────────────────────

@Composable
private fun QuestionnaireContent(
    uiState: QuestionnaireViewModel.UiState.Questionnaire,
    paddingValues: PaddingValues,
    viewModel: QuestionnaireViewModel,
    onBackClick: () -> Unit
) {
    val currentSelectedOption = viewModel.currentSelectedOption
        .collectAsStateWithLifecycle().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(MaterialTheme.dimension.spacing.medium),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // ── En-tête : progression + annulation ──
        QuestionnaireHeader(
            progress = uiState.progress,
            progressPercent = uiState.progressPercent,
            onBackClick = onBackClick
        )

        // ── Question animée ──
        AnimatedContent(
            targetState = uiState.currentQuestionIndex,
            transitionSpec = {
                if (targetState > initialState) {
                    slideInHorizontally(
                        initialOffsetX = { 1000 },
                        animationSpec = tween(300)
                    ) togetherWith slideOutHorizontally(
                        targetOffsetX = { -1000 },
                        animationSpec = tween(300)
                    )
                } else {
                    slideInHorizontally(
                        initialOffsetX = { -1000 },
                        animationSpec = tween(300)
                    ) togetherWith slideOutHorizontally(
                        targetOffsetX = { 1000 },
                        animationSpec = tween(300)
                    )
                }
            },
            label = "questionAnimation"
        ) { questionIndex ->
            QuestionContent(
                question = uiState.questionList[questionIndex],
                selectedOption = currentSelectedOption,
                onOptionSelected = { option ->
                    viewModel.dispatch(
                        QuestionnaireViewModel.Action.SelectOption(option)
                    )
                }
            )
        }

        // ── Navigation : Précédent / Suivant ──
        QuestionnaireNavigation(
            isFirstQuestion = uiState.isFirstQuestion,
            isLastQuestion = uiState.isLastQuestion,
            canProceed = currentSelectedOption.isNotBlank(),
            onPrevious = {
                viewModel.dispatch(QuestionnaireViewModel.Action.PreviousQuestion)
            },
            onNext = {
                viewModel.dispatch(QuestionnaireViewModel.Action.NextQuestion)
            }
        )
    }
}

// ─── EN-TÊTE ──────────────────────────────────────────────────────────────────

@Composable
private fun QuestionnaireHeader(
    progress: String,
    progressPercent: Float,
    onBackClick: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextBodyMedium(
                text = stringResource(R.string.questionnaire_title),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
            TextBodyMedium(
                text = progress,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        // Barre de progression
        LinearProgressIndicator(
            progress = { progressPercent },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}

// ─── CONTENU QUESTION ─────────────────────────────────────────────────────────

@Composable
private fun QuestionContent(
    question: Question,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.spacing.large)
    ) {
        // Texte de la question
        TextHeadlineLarge(
            text = question.text,
            modifier = Modifier.fillMaxWidth(),
            fontWeight = FontWeight.Medium
        )

        // Options A et B
        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.spacing.medium)
        ) {
            OptionCard(
                label = "A",
                text = question.optionA,
                isSelected = selectedOption == "A",
                onClick = { onOptionSelected("A") }
            )
            OptionCard(
                label = "B",
                text = question.optionB,
                isSelected = selectedOption == "B",
                onClick = { onOptionSelected("B") }
            )
        }
    }
}

// ─── CARTE OPTION ─────────────────────────────────────────────────────────────

@Composable
private fun OptionCard(
    label: String,
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 4.dp else 1.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.dimension.spacing.medium),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                MaterialTheme.dimension.spacing.medium
            )
        ) {
            // Badge A ou B
            Text(
                text = label,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) MaterialTheme.colorScheme.onPrimary
                else MaterialTheme.colorScheme.primary
            )

            TextTitleMedium(
                text = text,
                color = if (isSelected) MaterialTheme.colorScheme.onPrimary
                else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

// ─── NAVIGATION ───────────────────────────────────────────────────────────────

@Composable
private fun QuestionnaireNavigation(
    isFirstQuestion: Boolean,
    isLastQuestion: Boolean,
    canProceed: Boolean,
    onPrevious: () -> Unit,
    onNext: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Bouton retour
        CircularButton(
            params = CircularButtonParams(
                iconType = ButtonIconType.Vector(
                    Icons.AutoMirrored.Filled.ArrowBack
                ),
                backgroundColor = if (!isFirstQuestion)
                    MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.surfaceVariant,
                iconTint = MaterialTheme.colorScheme.onPrimary,
                onClick = onPrevious,
                description = stringResource(R.string.previous),
                enabled = !isFirstQuestion
            )
        )

        // Bouton suivant ou "Terminer"
        if (isLastQuestion) {
            FilledButton(
                onClick = onNext,
                text = stringResource(R.string.finish_questionnaire),
                enabled = canProceed,
                modifier = Modifier.height(
                    MaterialTheme.dimension.componentSize.buttonLarge
                )
            )
        } else {
            CircularButton(
                params = CircularButtonParams(
                    iconType = ButtonIconType.Vector(
                        Icons.AutoMirrored.Filled.ArrowForward
                    ),
                    backgroundColor = if (canProceed)
                        MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.surfaceVariant,
                    iconTint = MaterialTheme.colorScheme.onPrimary,
                    onClick = onNext,
                    description = stringResource(R.string.next),
                    enabled = canProceed
                )
            )
        }
    }
}