package com.amadiyawa.feature_personnality.data.datasource.remote

import com.amadiyawa.feature_personnality.data.dto.AiDescriptionRequest
import com.amadiyawa.feature_personnality.data.dto.AiDescriptionResponse
import com.amadiyawa.feature_personnality.domain.model.UserInfo
import timber.log.Timber

internal class PersonalityApiDataSource(
    private val personalityApiService: PersonalityApiService
) {
    suspend fun getAiDescription(
        mbtiType: String,
        userInfo: UserInfo
    ): AiDescriptionResponse {
        Timber.d("Appel API → mbtiType: $mbtiType, user: ${userInfo.firstName}")

        return personalityApiService.submitPersonality(
            AiDescriptionRequest(
                mbtiType = mbtiType,
                firstName = userInfo.firstName,
                age = userInfo.age,
                city = userInfo.city,
                situation = userInfo.situation.name
            )
        )
    }
}