package com.amadiyawa.feature_personnality.presentation.screen.userinfo

import com.amadiyawa.feature_personnality.R
import com.amadiyawa.feature_personnality.domain.model.UserSituation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amadiyawa.feature_base.presentation.compose.composable.FilledButton
import com.amadiyawa.feature_base.presentation.compose.composable.LoadingAnimation
import com.amadiyawa.feature_base.presentation.compose.composable.TextBodyMedium
import com.amadiyawa.feature_base.presentation.compose.composable.TextHeadlineLarge
import com.amadiyawa.feature_base.presentation.compose.composable.TextTitleMedium
import com.amadiyawa.feature_base.presentation.theme.dimension
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun UserInfoScreen(
    mbtiType: String,
    onResultReady: (String) -> Unit
) {
    val viewModel: UserInfoViewModel = koinViewModel()
    val uiState = viewModel.uiStateFlow.collectAsStateWithLifecycle().value

    // Navigation vers ResultScreen quand l'IA a répondu
    LaunchedEffect(uiState) {
        if (uiState is UserInfoViewModel.UiState.Success) {
            onResultReady(uiState.mbtiType)
        }
    }

    Scaffold(
        contentColor = MaterialTheme.colorScheme.onBackground,
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { paddingValues ->
        HandleUiState(
            uiState = uiState,
            paddingValues = paddingValues,
            mbtiType = mbtiType,
            viewModel = viewModel
        )
    }
}

// ─── ÉTATS ────────────────────────────────────────────────────────────────────

@Composable
private fun HandleUiState(
    uiState: UserInfoViewModel.UiState,
    paddingValues: PaddingValues,
    mbtiType: String,
    viewModel: UserInfoViewModel
) {
    when (uiState) {
        is UserInfoViewModel.UiState.Form -> {
            FormContent(
                uiState = uiState,
                paddingValues = paddingValues,
                mbtiType = mbtiType,
                viewModel = viewModel
            )
        }

        is UserInfoViewModel.UiState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(
                        MaterialTheme.dimension.spacing.medium
                    )
                ) {
                    LoadingAnimation(visible = true)
                    TextBodyMedium(
                        text = stringResource(R.string.userinfo_loading),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
        }

        is UserInfoViewModel.UiState.Success -> {
            // Navigation gérée par LaunchedEffect
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                LoadingAnimation(visible = true)
            }
        }

        is UserInfoViewModel.UiState.Error -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.userinfo_error),
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

// ─── FORMULAIRE ───────────────────────────────────────────────────────────────

@Composable
private fun FormContent(
    uiState: UserInfoViewModel.UiState.Form,
    paddingValues: PaddingValues,
    mbtiType: String,
    viewModel: UserInfoViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(MaterialTheme.dimension.spacing.medium)
            .verticalScroll(rememberScrollState())
            .imePadding(),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.spacing.large)
    ) {
        // ── En-tête ──
        FormHeader(mbtiType = mbtiType)

        // ── Champ Prénom ──
        OutlinedTextField(
            value = uiState.firstName,
            onValueChange = {
                viewModel.dispatch(UserInfoViewModel.Action.FirstNameChanged(it))
            },
            label = { Text(stringResource(R.string.userinfo_firstname)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words
            )
        )

        // ── Champ Âge ──
        OutlinedTextField(
            value = uiState.age,
            onValueChange = {
                viewModel.dispatch(UserInfoViewModel.Action.AgeChanged(it))
            },
            label = { Text(stringResource(R.string.userinfo_age)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            isError = uiState.age.isNotBlank() &&
                    (uiState.age.toIntOrNull() == null ||
                            uiState.age.toInt() !in 10..80),
            supportingText = {
                if (uiState.age.isNotBlank() && uiState.age.toIntOrNull() == null) {
                    Text(stringResource(R.string.userinfo_age_error))
                }
            }
        )

        // ── Champ Ville ──
        OutlinedTextField(
            value = uiState.city,
            onValueChange = {
                viewModel.dispatch(UserInfoViewModel.Action.CityChanged(it))
            },
            label = { Text(stringResource(R.string.userinfo_city)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words
            )
        )

        // ── Sélection situation ──
        SituationSelector(
            selected = uiState.situation,
            onSelected = {
                viewModel.dispatch(UserInfoViewModel.Action.SituationChanged(it))
            }
        )

        // ── Bouton Valider ──
        FilledButton(
            onClick = {
                viewModel.dispatch(UserInfoViewModel.Action.Submit(mbtiType))
            },
            text = stringResource(R.string.userinfo_submit),
            enabled = uiState.isValid,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

// ─── EN-TÊTE ──────────────────────────────────────────────────────────────────

@Composable
private fun FormHeader(mbtiType: String) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = mbtiType,
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        TextHeadlineLarge(
            text = stringResource(R.string.userinfo_title)
        )
        TextBodyMedium(
            text = stringResource(R.string.userinfo_subtitle),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}

// ─── SÉLECTEUR SITUATION ──────────────────────────────────────────────────────

@Composable
private fun SituationSelector(
    selected: UserSituation,
    onSelected: (UserSituation) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimension.spacing.small)
    ) {
        TextTitleMedium(
            text = stringResource(R.string.userinfo_situation),
            fontWeight = FontWeight.SemiBold
        )

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(
                MaterialTheme.dimension.spacing.small
            ),
            verticalArrangement = Arrangement.spacedBy(
                MaterialTheme.dimension.spacing.small
            )
        ) {
            UserSituation.entries.forEach { situation ->
                FilterChip(
                    selected = selected == situation,
                    onClick = { onSelected(situation) },
                    label = {
                        Text(
                            text = situation.label(),
                            style = MaterialTheme.typography.bodySmall
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        }
    }
}

// Extension pour afficher le label traduit de chaque situation
@Composable
private fun UserSituation.label(): String = when (this) {
    UserSituation.STUDENT -> stringResource(R.string.situation_student)
    UserSituation.EMPLOYED -> stringResource(R.string.situation_employed)
    UserSituation.SELF_EMPLOYED -> stringResource(R.string.situation_self_employed)
    UserSituation.SEEKING -> stringResource(R.string.situation_seeking)
}