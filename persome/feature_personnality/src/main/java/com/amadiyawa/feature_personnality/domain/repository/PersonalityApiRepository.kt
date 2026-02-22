package com.amadiyawa.feature_personnality.domain.repository

import com.amadiyawa.feature_base.domain.result.OperationResult
import com.amadiyawa.feature_personnality.domain.model.UserInfo

internal interface PersonalityApiRepository {
    suspend fun getAiDescription(
        mbtiType: String,
        userInfo: UserInfo
    ): OperationResult<String>
}