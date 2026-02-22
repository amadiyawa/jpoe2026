package com.amadiyawa.feature_personnality.presentation.screen.history

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import com.amadiyawa.feature_base.common.resources.Dimen
import com.amadiyawa.feature_base.presentation.common.getIconSize
import com.amadiyawa.feature_base.presentation.compose.composable.LoadingAnimation
import com.amadiyawa.feature_base.presentation.compose.composable.TextBodyMedium
import com.amadiyawa.feature_base.presentation.compose.composable.TextHeadlineLarge
import com.amadiyawa.feature_base.presentation.compose.composable.TextTitleLarge
import com.amadiyawa.feature_base.presentation.compose.composable.TextTitleMedium
import com.amadiyawa.feature_base.presentation.theme.dimension
import com.amadiyawa.feature_personnality.R
import com.amadiyawa.feature_personnality.domain.model.MbtiResult
import com.amadiyawa.feature_personnality.presentation.util.formatDate
import com.amadiyawa.feature_personnality.presentation.util.formatTime
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun HistoryScreen(
    onStartTest: () -> Unit,
    onResultClick: (String) -> Unit
) {
    val viewModel: HistoryViewModel = koinViewModel()

    // Charge l'historique à chaque fois que l'écran est affiché
    LaunchedEffect(Unit) {
        viewModel.onEnter()
    }

    val uiState = viewModel.uiStateFlow.collectAsStateWithLifecycle().value

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        // Bouton fixe en bas : "Commencer le test"
        bottomBar = {
            StartTestButton(onClick = onStartTest)
        }
    ) { paddingValues ->
        HandleUiState(
            uiState = uiState,
            paddingValues = paddingValues,
            onResultClick = onResultClick
        )
    }
}

// ─── ÉTATS ────────────────────────────────────────────────────────────────────

@Composable
private fun HandleUiState(
    uiState: HistoryViewModel.UiState,
    paddingValues: PaddingValues,
    onResultClick: (String) -> Unit
) {
    when (uiState) {
        is HistoryViewModel.UiState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                LoadingAnimation(visible = true)
            }
        }

        is HistoryViewModel.UiState.Empty -> {
            EmptyHistory(paddingValues = paddingValues)
        }

        is HistoryViewModel.UiState.Error -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.error_loading_history),
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        is HistoryViewModel.UiState.History -> {
            HistoryList(
                resultList = uiState.resultList,
                paddingValues = paddingValues,
                onResultClick = onResultClick
            )
        }
    }
}

// ─── ÉTAT VIDE ────────────────────────────────────────────────────────────────

@Composable
private fun EmptyHistory(paddingValues: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        val iconSize = getIconSize()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.spacing.medium)
        ) {
            Icon(
                imageVector = Icons.Default.Psychology,
                contentDescription = null,
                modifier = Modifier.size(iconSize), // ← plus de padding ici,
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
            )
            TextHeadlineLarge(
                text = stringResource(R.string.history_empty_title)
            )
            TextBodyMedium(
                text = stringResource(R.string.history_empty_message),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

// ─── LISTE DES RÉSULTATS ──────────────────────────────────────────────────────

@Composable
private fun HistoryList(
    resultList: List<MbtiResult>,
    paddingValues: PaddingValues,
    onResultClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentPadding = PaddingValues(Dimen.Padding.screenContent),
        verticalArrangement = Arrangement.spacedBy(Dimen.Spacing.medium)
    ) {
        item {
            TextHeadlineLarge(
                text = stringResource(R.string.history_title),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = Dimen.Spacing.medium)
            )
        }

        items(resultList) { result ->
            ResultCard(
                result = result,
                onClick = { onResultClick(result.mbtiType) }
            )
        }
    }
}

// ─── CARTE RÉSULTAT ───────────────────────────────────────────────────────────

@Composable
private fun ResultCard(
    result: MbtiResult,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimen.Padding.screenContent),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Badge type MBTI
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .width(64.dp)
                    .padding(end = Dimen.Spacing.medium)
            ) {
                TextTitleLarge(
                    text = result.mbtiType,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }

            // Infos utilisateur + date
            Column(modifier = Modifier.weight(1f)) {
                TextTitleMedium(
                    text = result.userInfo.firstName,
                    fontWeight = FontWeight.SemiBold
                )
                TextBodyMedium(
                    text = "${result.userInfo.city} · ${result.userInfo.age} ans",
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
                TextBodyMedium(
                    text = formatDate(result.createdDate) + " " + formatTime(result.createdDate),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                )
            }

            // Indicateur description IA ou statique
            Text(
                text = if (result.aiDescription != null) "✨" else "📄",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

// ─── BOUTON BAS DE PAGE ───────────────────────────────────────────────────────

@Composable
private fun StartTestButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimen.Padding.screenContent)
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(Dimen.Spacing.small))
            Text(
                text = stringResource(R.string.start_test),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}