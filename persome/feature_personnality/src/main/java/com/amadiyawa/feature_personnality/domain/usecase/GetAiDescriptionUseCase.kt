package com.amadiyawa.feature_personnality.domain.usecase

import com.amadiyawa.feature_base.domain.result.OperationResult
import com.amadiyawa.feature_personnality.domain.model.UserInfo
import com.amadiyawa.feature_personnality.domain.repository.PersonalityApiRepository

internal class GetAiDescriptionUseCase(
    private val personalityApiRepository: PersonalityApiRepository
) {
    suspend operator fun invoke(
        mbtiType: String,
        userInfo: UserInfo
    ): OperationResult<String> {
        return personalityApiRepository.getAiDescription(
            mbtiType = mbtiType,
            userInfo = userInfo
        )
    }
}