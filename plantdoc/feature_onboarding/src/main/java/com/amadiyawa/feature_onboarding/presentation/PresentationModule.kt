package com.amadiyawa.feature_onboarding.presentation

import com.amadiyawa.feature_onboarding.presentation.screen.onboarding.OnboardingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val presentationModule = module {
    // ViewModel
    viewModel {
        OnboardingViewModel(
            getOnboardingUseCase = get(),
            dataStoreManager = get()
        )
    }
}