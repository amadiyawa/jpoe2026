package com.amadiyawa.feature_onboarding.presentation.screen.onboarding

sealed class OnboardingUiEvent {
    object NavigateToAuth : OnboardingUiEvent()
    data class ShowError(val message: String) : OnboardingUiEvent()
    data class RequestPermissions(val permissions: List<String>) : OnboardingUiEvent()
    data class ShowPermissionsBlocked(val deniedPermissions: List<String>) : OnboardingUiEvent()
    object OpenSettings : OnboardingUiEvent()
}