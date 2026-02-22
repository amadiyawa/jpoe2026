package com.amadiyawa.feature_personnality.data.datasource.remote

import com.amadiyawa.feature_personnality.data.dto.AiDescriptionRequest
import com.amadiyawa.feature_personnality.data.dto.AiDescriptionResponse
import retrofit2.http.Body
import retrofit2.http.POST

internal interface PersonalityApiService {

    @POST("api/v1/personality/submit")
    suspend fun submitPersonality(
        @Body request: AiDescriptionRequest
    ): AiDescriptionResponse
}