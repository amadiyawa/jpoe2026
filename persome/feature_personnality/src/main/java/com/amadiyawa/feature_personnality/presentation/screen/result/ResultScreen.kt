package com.amadiyawa.feature_personnality.presentation.screen.result

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import com.amadiyawa.feature_base.presentation.compose.composable.FilledButton
import com.amadiyawa.feature_base.presentation.compose.composable.LoadingAnimation
import com.amadiyawa.feature_base.presentation.compose.composable.TextBodyLarge
import com.amadiyawa.feature_base.presentation.compose.composable.TextBodyMedium
import com.amadiyawa.feature_base.presentation.compose.composable.TextHeadlineLarge
import com.amadiyawa.feature_base.presentation.compose.composable.TextTitleMedium
import com.amadiyawa.feature_base.presentation.theme.dimension
import com.amadiyawa.feature_personnality.R
import com.amadiyawa.feature_personnality.domain.model.MbtiResult
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun ResultScreen(
    mbtiType: String,
    onRetakeTest: () -> Unit,
    onBackClick: () -> Unit
) {
    val viewModel: ResultViewModel = koinViewModel()

    LaunchedEffect(mbtiType) {
        viewModel.onEnter(mbtiType)
    }

    val uiState = viewModel.uiStateFlow.collectAsStateWithLifecycle().value

    Scaffold(
        contentColor = MaterialTheme.colorScheme.onBackground,
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { paddingValues ->
        HandleUiState(
            uiState = uiState,
            paddingValues = paddingValues,
            onRetakeTest = onRetakeTest,
            onBackToHistory = onBackClick
        )
    }
}

// ─── ÉTATS ────────────────────────────────────────────────────────────────────

@Composable
private fun HandleUiState(
    uiState: ResultViewModel.UiState,
    paddingValues: PaddingValues,
    onRetakeTest: () -> Unit,
    onBackToHistory: () -> Unit
) {
    when (uiState) {
        is ResultViewModel.UiState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                LoadingAnimation(visible = true)
            }
        }

        is ResultViewModel.UiState.Error -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.result_error),
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        is ResultViewModel.UiState.Result -> {
            ResultContent(
                mbtiResult = uiState.mbtiResult,
                paddingValues = paddingValues,
                onRetakeTest = onRetakeTest,
                onBackToHistory = onBackToHistory
            )
        }
    }
}

// ─── CONTENU PRINCIPAL ────────────────────────────────────────────────────────

@Composable
private fun ResultContent(
    mbtiResult: MbtiResult,
    paddingValues: PaddingValues,
    onRetakeTest: () -> Unit,
    onBackToHistory: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(MaterialTheme.dimension.spacing.medium)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.spacing.large)
    ) {
        // ── Badge type MBTI ──
        MbtiTypeBadge(
            mbtiType = mbtiResult.mbtiType,
            firstName = mbtiResult.userInfo.firstName
        )

        HorizontalDivider()

        // ── Description IA ou statique ──
        DescriptionSection(mbtiResult = mbtiResult)

        HorizontalDivider()

        // ── Actions ──
        ResultActions(
            onRetakeTest = onRetakeTest,
            onBackToHistory = onBackToHistory
        )
    }
}

// ─── BADGE MBTI ───────────────────────────────────────────────────────────────

@Composable
private fun MbtiTypeBadge(
    mbtiType: String,
    firstName: String
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = mbtiType,
            style = MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.primary
        )
        TextHeadlineLarge(
            text = stringResource(R.string.result_greeting, firstName)
        )
        TextBodyMedium(
            text = stringResource(R.string.result_subtitle),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}

// ─── SECTION DESCRIPTION ──────────────────────────────────────────────────────

@Composable
private fun DescriptionSection(mbtiResult: MbtiResult) {
    val isAiDescription = mbtiResult.aiDescription != null
    val descriptionText = mbtiResult.aiDescription ?: mbtiResult.staticDescription

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.dimension.spacing.medium),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.spacing.medium)
        ) {
            // Badge IA ou statique
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = if (isAiDescription) Icons.Default.AutoAwesome
                    else Icons.Default.Description,
                    contentDescription = null,
                    tint = if (isAiDescription) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                TextTitleMedium(
                    text = if (isAiDescription) {
                        stringResource(R.string.result_ai_description)
                    } else {
                        stringResource(R.string.result_static_description)
                    },
                    color = if (isAiDescription) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    fontWeight = FontWeight.SemiBold
                )
            }

            HorizontalDivider()

            // Texte de la description
            TextBodyLarge(
                text = descriptionText,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

// ─── ACTIONS ──────────────────────────────────────────────────────────────────

@Composable
private fun ResultActions(
    onRetakeTest: () -> Unit,
    onBackToHistory: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.spacing.small)
    ) {
        // Refaire le test
        FilledButton(
            onClick = onRetakeTest,
            text = stringResource(R.string.result_retake),
            modifier = Modifier.fillMaxWidth()
        )

        // Retour à l'historique
        OutlinedButton(
            onClick = onBackToHistory,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = stringResource(R.string.result_back_history))
        }
    }
}