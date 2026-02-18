package com.amadiyawa.feature_onboarding.data.repository

import com.amadiyawa.feature_base.domain.result.OperationResult
import com.amadiyawa.feature_onboarding.domain.model.OnboardingScreen
import com.amadiyawa.feature_onboarding.domain.repository.OnboardingRepository
import com.amadiyawa.onboarding.R
import timber.log.Timber

internal class OnboardingRepositoryImpl : OnboardingRepository {
    override suspend fun getOnboardList(): OperationResult<List<OnboardingScreen>> {
        return try {
            val onboarding = listOf(
                // Screen 1: Welcome
                OnboardingScreen(
                    id = "welcome",
                    titleResId = R.string.onboard_title_1,
                    descriptionResId = R.string.onboard_description_1,
                    imageResId = R.drawable.onboard_image_1
                ),
                // Screen 2: How It Works
                OnboardingScreen(
                    id = "how_it_works",
                    titleResId = R.string.onboard_title_2,
                    descriptionResId = R.string.onboard_description_2,
                    imageResId = R.drawable.onboard_image_2
                ),
                // Screen 3: Permissions (with request)
                OnboardingScreen(
                    id = "benefits",
                    titleResId = R.string.onboard_title_3,
                    descriptionResId = R.string.onboard_description_3,
                    imageResId = R.drawable.onboard_image_3
                ),
                // Screen 4: Ready to Start
                OnboardingScreen(
                    id = "permissions",
                    titleResId = R.string.onboard_title_4,
                    descriptionResId = R.string.onboard_description_4,
                    imageResId = R.drawable.onboard_image_4,
                    requiresPermissions = true,
                    permissions = listOf(
                        android.Manifest.permission.SEND_SMS,
                        android.Manifest.permission.READ_SMS,
                        android.Manifest.permission.READ_PHONE_STATE
                    )
                )
            )
            OperationResult.Success(onboarding)
        } catch (e: Exception) {
            Timber.e(e, "Error loading onboarding screens")
            OperationResult.error(
                throwable = e,
                message = "Failed to load onboarding"
            )
        }
    }
}