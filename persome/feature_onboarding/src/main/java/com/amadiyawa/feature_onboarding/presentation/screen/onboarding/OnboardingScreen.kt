package com.amadiyawa.feature_onboarding.presentation.screen.onboarding

import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.amadiyawa.feature_base.presentation.compose.composable.ButtonIconType
import com.amadiyawa.feature_base.presentation.compose.composable.CircularButton
import com.amadiyawa.feature_base.presentation.compose.composable.CircularButtonParams
import com.amadiyawa.feature_base.presentation.compose.composable.ErrorState
import com.amadiyawa.feature_base.presentation.compose.composable.FilledButton
import com.amadiyawa.feature_base.presentation.compose.composable.LoadingAnimation
import com.amadiyawa.feature_base.presentation.compose.composable.ProgressionIndicator
import com.amadiyawa.feature_base.presentation.compose.composable.TextBodyLarge
import com.amadiyawa.feature_base.presentation.compose.composable.TextHeadlineLarge
import com.amadiyawa.feature_base.presentation.theme.dimension
import com.amadiyawa.feature_onboarding.domain.model.OnboardingScreen
import com.amadiyawa.feature_onboarding.domain.model.toImageVector
import com.amadiyawa.feature_onboarding.presentation.components.getOnboardingIconSize
import com.amadiyawa.onboarding.R
import org.koin.androidx.compose.koinViewModel

/**
 * Écran principal de l'onboarding.
 * Affiche les slides de présentation de l'app Persome.
 */
@Composable
internal fun OnboardingScreen(
    onFinished: () -> Unit,
    viewModel: OnboardingViewModel = koinViewModel()
) {
    val state by viewModel.uiStateFlow.collectAsState()
    val currentScreen = state.currentScreen
    val context = LocalContext.current

    // Collecte les événements one-shot du ViewModel
    LaunchedEffect(key1 = Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is OnboardingUiEvent.NavigateToAuth -> onFinished()
                is OnboardingUiEvent.ShowError -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        when {
            // État chargement
            state.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    LoadingAnimation(visible = true)
                }
            }

            // État normal : affichage des slides
            currentScreen != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(MaterialTheme.dimension.spacing.medium),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // Indicateur de progression (ex: ● ○ ○ ○)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = MaterialTheme.dimension.spacing.small)
                    ) {
                        ProgressionIndicator(
                            currentLevel = state.currentScreenIndex,
                            totalLevels = state.screens.size - 1
                        )
                    }

                    // Contenu de la slide courante
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        OnboardingContent(screen = currentScreen)
                    }

                    // Boutons de navigation (Précédent / Suivant / Commencer)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = MaterialTheme.dimension.spacing.small)
                    ) {
                        OnboardingActions(
                            isFirstScreen = state.isFirstScreen,
                            isLastScreen = state.isLastScreen,
                            onPrevious = { viewModel.dispatch(OnboardingAction.PreviousScreen) },
                            onNext = { viewModel.dispatch(OnboardingAction.NextScreen) },
                            onFinish = { viewModel.dispatch(OnboardingAction.CompleteOnboarding) }
                        )
                    }
                }
            }

            // État erreur
            state.error != null -> {
                ErrorState(
                    onRetry = { viewModel.dispatch(OnboardingAction.LoadScreens) },
                    errorMessage = state.error
                )
            }
        }
    }
}

/**
 * Contenu d'une slide : icône + titre + description.
 */
@Composable
fun OnboardingContent(
    screen: OnboardingScreen,
    isLarge: Boolean = false
) {
    val iconSize = getOnboardingIconSize()

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Icône Material Design associée à la slide
        screen.iconType?.let { iconType ->
            Icon(
                imageVector = iconType.toImageVector(),
                contentDescription = null,
                modifier = Modifier.size(iconSize * if (isLarge) 1.3f else 1f), // ← plus de padding ici
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(MaterialTheme.dimension.spacing.xLarge))
        }

        TextHeadlineLarge(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = screen.titleResId),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(MaterialTheme.dimension.spacing.medium))

        TextBodyLarge(
            text = stringResource(id = screen.descriptionResId),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            textAlign = TextAlign.Center
        )
    }
}

/**
 * Boutons de navigation : Précédent (←), Suivant (→), Commencer (dernier écran).
 */
@Composable
fun OnboardingActions(
    isFirstScreen: Boolean,
    isLastScreen: Boolean,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    onFinish: () -> Unit
) {
    val backButtonColor by animateColorAsState(
        targetValue = if (!isFirstScreen) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.surfaceVariant,
        label = "backButtonColor"
    )

    val nextButtonColor by animateColorAsState(
        targetValue = if (!isLastScreen) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.surfaceVariant,
        label = "nextButtonColor"
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Bouton retour
        CircularButton(
            params = CircularButtonParams(
                iconType = ButtonIconType.Vector(Icons.AutoMirrored.Filled.ArrowBack),
                backgroundColor = backButtonColor,
                iconTint = MaterialTheme.colorScheme.onPrimary,
                onClick = onPrevious,
                description = stringResource(id = R.string.previous),
                enabled = !isFirstScreen
            )
        )

        // Bouton central : "Commencer" sur la dernière slide, sinon invisible
        AnimatedContent(
            targetState = isLastScreen,
            transitionSpec = { fadeIn() togetherWith fadeOut() },
            label = "finishButtonAnimation"
        ) { isLast ->
            if (isLast) {
                FilledButton(
                    modifier = Modifier.height(MaterialTheme.dimension.componentSize.buttonLarge),
                    onClick = onFinish,
                    text = stringResource(id = R.string.get_started),
                    enabled = true
                )
            } else {
                Spacer(modifier = Modifier.width(48.dp))
            }
        }

        // Bouton suivant
        CircularButton(
            params = CircularButtonParams(
                iconType = ButtonIconType.Vector(Icons.AutoMirrored.Filled.ArrowForward),
                backgroundColor = nextButtonColor,
                iconTint = MaterialTheme.colorScheme.onPrimary,
                onClick = onNext,
                description = stringResource(id = R.string.next),
                enabled = !isLastScreen
            )
        )
    }
}