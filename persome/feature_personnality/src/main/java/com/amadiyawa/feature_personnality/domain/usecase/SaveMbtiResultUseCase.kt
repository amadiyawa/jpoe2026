package com.amadiyawa.feature_personnality.domain.usecase

import com.amadiyawa.feature_personnality.domain.model.MbtiResult
import com.amadiyawa.feature_personnality.domain.repository.MbtiResultRepository

internal class SaveMbtiResultUseCase(
    private val mbtiResultRepository: MbtiResultRepository
) {
    suspend operator fun invoke(result: MbtiResult) {
        mbtiResultRepository.insertResult(result = result)
    }
}