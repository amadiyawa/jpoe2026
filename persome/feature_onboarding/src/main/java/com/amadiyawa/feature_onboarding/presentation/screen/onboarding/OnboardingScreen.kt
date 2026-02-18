package com.amadiyawa.feature_onboarding.presentation.screen.onboarding

import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Sms
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.amadiyawa.feature_base.domain.permission.PermissionHandler
import com.amadiyawa.feature_base.presentation.compose.composable.ButtonIconType
import com.amadiyawa.feature_base.presentation.compose.composable.CircularButton
import com.amadiyawa.feature_base.presentation.compose.composable.CircularButtonParams
import com.amadiyawa.feature_base.presentation.compose.composable.ErrorState
import com.amadiyawa.feature_base.presentation.compose.composable.FilledButton
import com.amadiyawa.feature_base.presentation.compose.composable.LoadingAnimation
import com.amadiyawa.feature_base.presentation.compose.composable.ProgressionIndicator
import com.amadiyawa.feature_base.presentation.compose.composable.TextBodyLarge
import com.amadiyawa.feature_base.presentation.compose.composable.TextBodyMedium
import com.amadiyawa.feature_base.presentation.compose.composable.TextHeadlineLarge
import com.amadiyawa.feature_base.presentation.helper.findActivity
import com.amadiyawa.feature_base.presentation.theme.dimension
import com.amadiyawa.feature_onboarding.domain.model.OnboardingScreen
import com.amadiyawa.feature_onboarding.presentation.components.PermissionsBlockedDialog
import com.amadiyawa.feature_onboarding.presentation.components.getOnboardingImageSize
import com.amadiyawa.onboarding.R
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf
import timber.log.Timber
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner

/**
 * Main onboarding screen composable.
 * Displays onboarding flow with permission handling.
 */
@Composable
internal fun OnboardingScreen(
    onFinished: () -> Unit,
    viewModel: OnboardingViewModel = koinViewModel()
) {
    val state by viewModel.uiStateFlow.collectAsState()
    val currentScreen = state.currentScreen
    val context = LocalContext.current
    val activity = LocalContext.current.findActivity()

    val permissionHandler: PermissionHandler = koinInject {
        parametersOf(activity)
    }

    var showBlockedDialog by remember { mutableStateOf(false) }
    var blockedPermissions by remember { mutableStateOf<List<String>>(emptyList()) }

    LaunchedEffect(currentScreen) {
        if (currentScreen?.requiresPermissions == true) {
            val realState = currentScreen.permissions.associateWith { permission ->
                permissionHandler.isPermissionGranted(permission)
            }
            viewModel.syncPermissionsState(realState)
        }
    }

    // Collect one-time events
    LaunchedEffect(key1 = Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is OnboardingUiEvent.NavigateToAuth -> onFinished()

                is OnboardingUiEvent.ShowError -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }

                is OnboardingUiEvent.RequestPermissions -> {
                    Timber.d("Launching permission request for: ${event.permissions.joinToString()}")
                    val results = permissionHandler.requestPermissions(
                        *event.permissions.toTypedArray()
                    )

                    val denied = event.permissions.filter { permission ->
                        !results[permission]!! && !permissionHandler.shouldShowRationale(permission)
                    }

                    viewModel.handlePermissionsResult(results, denied)
                }

                is OnboardingUiEvent.ShowPermissionsBlocked -> {
                    blockedPermissions = event.deniedPermissions
                    showBlockedDialog = true
                }

                is OnboardingUiEvent.OpenSettings -> {
                    permissionHandler.openAppSettings()
                }
            }
        }
    }

    if (showBlockedDialog) {
        PermissionsBlockedDialog(
            blockedPermissions = blockedPermissions,
            onOpenSettings = {
                showBlockedDialog = false
                viewModel.dispatch(OnboardingAction.OpenSettings)
            },
            onDismiss = {
                showBlockedDialog = false
            }
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    LoadingAnimation(visible = true)
                }
            }
            currentScreen != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(MaterialTheme.dimension.spacing.medium),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // Progression indicator
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

                    // Onboarding content
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        OnboardingContent(
                            screen = currentScreen,
                            permissionsState = viewModel.permissionsState
                        )
                    }

                    // Bottom actions
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = MaterialTheme.dimension.spacing.small)
                    ) {
                        OnboardingActions(
                            isFirstScreen = state.isFirstScreen,
                            isLastScreen = state.isLastScreen,
                            canProceed = viewModel.canProceed(),
                            isPermissionsScreen = currentScreen.requiresPermissions,
                            onPrevious = { viewModel.dispatch(OnboardingAction.PreviousScreen) },
                            onNext = { viewModel.dispatch(OnboardingAction.NextScreen) },
                            onFinish = {
                                if (currentScreen.requiresPermissions && !viewModel.permissionsGranted) { viewModel.dispatch(OnboardingAction.RequestPermissions)
                                } else {
                                    viewModel.dispatch(OnboardingAction.CompleteOnboarding)
                                }
                            }
                        )
                    }
                }
            }
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
 * Displays onboarding content based on screen type.
 */
@Composable
fun OnboardingContent(
    screen: OnboardingScreen,
    permissionsState: Map<String, Boolean> = emptyMap(),
    isLarge: Boolean = false
) {
    val imageSize = getOnboardingImageSize(isLarge)

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (screen.requiresPermissions) {
            PermissionsContent(
                permissions = screen.permissions,
                permissionsState = permissionsState
            )
        } else {
            // Standard onboarding screen
            Image(
                painter = painterResource(id = screen.imageResId),
                contentDescription = null,
                modifier = Modifier
                    .size(width = imageSize.width.dp, height = imageSize.height.dp)
                    .padding(bottom = MaterialTheme.dimension.spacing.xLarge)
            )

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
}

/**
 * Displays permissions request UI with status indicators.
 */
@Composable
fun PermissionsContent(
    permissions: List<String>,
    permissionsState: Map<String, Boolean>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.dimension.spacing.medium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Security,
            contentDescription = null,
            modifier = Modifier.size(120.dp),
            tint = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(MaterialTheme.dimension.spacing.large))

        TextHeadlineLarge(
            text = stringResource(R.string.permissions_required),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(MaterialTheme.dimension.spacing.medium))

        getGroupedPermissions(permissions).forEach { (groupKey, groupPermissions) ->
            val isGranted = groupPermissions.all { permissionsState[it] == true }

            PermissionItem(
                icon = getPermissionIcon(groupKey),
                title = getPermissionTitle(groupKey),
                description = getPermissionDescription(groupKey),
                isGranted = isGranted
            )

            Spacer(modifier = Modifier.height(MaterialTheme.dimension.spacing.small))
        }
    }
}

/**
 * Individual permission item with status indicator.
 */
@Composable
fun PermissionItem(
    icon: ImageVector,
    title: String,
    description: String,
    isGranted: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = MaterialTheme.dimension.spacing.small),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(32.dp),
            tint = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.width(MaterialTheme.dimension.spacing.medium))

        Column(modifier = Modifier.weight(1f)) {
            TextBodyLarge(
                text = title,
                fontWeight = FontWeight.SemiBold
            )
            TextBodyMedium(
                text = description,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }

        Spacer(modifier = Modifier.width(MaterialTheme.dimension.spacing.small))

        Icon(
            imageVector = if (isGranted) {
                Icons.Default.CheckCircle
            } else {
                Icons.Default.Cancel
            },
            contentDescription = if (isGranted) "Granted" else "Not granted",
            modifier = Modifier.size(24.dp),
            tint = if (isGranted) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.onSurfaceVariant
            }
        )
    }
}

/**
 * Bottom navigation actions for onboarding.
 */
@Composable
fun OnboardingActions(
    isFirstScreen: Boolean,
    isLastScreen: Boolean,
    canProceed: Boolean,
    isPermissionsScreen: Boolean = false,
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

        Row {
            AnimatedContent(
                targetState = Pair(isLastScreen, isPermissionsScreen),
                transitionSpec = {
                    fadeIn() togetherWith fadeOut()
                },
                label = "actionButtonAnimation"
            ) { (last, permissions) ->
                when {
                    last && permissions -> {
                        FilledButton(
                            modifier = Modifier.height(MaterialTheme.dimension.componentSize.buttonLarge),
                            onClick = onFinish,
                            text = stringResource(id = R.string.grant_permissions),
                            enabled = true
                        )
                    }
                    last -> {
                        FilledButton(
                            modifier = Modifier.height(MaterialTheme.dimension.componentSize.buttonLarge),
                            onClick = onFinish,
                            text = stringResource(id = R.string.get_started),
                            enabled = canProceed
                        )
                    }
                    else -> {
                        Spacer(modifier = Modifier.width(48.dp))
                    }
                }
            }
        }

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

/**
 * Groups similar permissions together (e.g., SEND_SMS + READ_SMS → "SMS").
 */
private fun getGroupedPermissions(permissions: List<String>): Map<String, List<String>> {
    val groups = mutableMapOf<String, MutableList<String>>()

    permissions.forEach { permission ->
        val groupKey = when {
            permission.contains("SMS") -> "SMS"
            permission.contains("PHONE") -> "PHONE"
            else -> permission
        }

        groups.getOrPut(groupKey) { mutableListOf() }.add(permission)
    }

    return groups
}

/**
 * Helper functions for permission mapping.
 */
@Composable
private fun getPermissionIcon(permission: String): ImageVector {
    return when {
        permission.contains("SMS") -> Icons.Default.Sms
        permission.contains("PHONE") -> Icons.Default.PhoneAndroid
        else -> Icons.Default.Security
    }
}

@Composable
private fun getPermissionTitle(permission: String): String {
    return when {
        permission.contains("SMS") -> stringResource(R.string.permission_sms_title)
        permission.contains("PHONE") -> stringResource(R.string.permission_phone_title)
        else -> permission.substringAfterLast(".")
    }
}

@Composable
private fun getPermissionDescription(permission: String): String {
    return when {
        permission.contains("SMS") -> stringResource(R.string.permission_sms_description)
        permission.contains("PHONE") -> stringResource(R.string.permission_phone_description)
        else -> ""
    }
}