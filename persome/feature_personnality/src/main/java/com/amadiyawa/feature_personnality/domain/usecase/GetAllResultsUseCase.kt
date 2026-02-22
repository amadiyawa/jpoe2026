package com.amadiyawa.feature_personnality.domain.usecase

import com.amadiyawa.feature_base.domain.result.OperationResult
import com.amadiyawa.feature_personnality.domain.model.MbtiResult
import com.amadiyawa.feature_personnality.domain.repository.MbtiResultRepository


internal class GetAllResultsUseCase(
    private val mbtiResultRepository: MbtiResultRepository
) {
    suspend operator fun invoke(): OperationResult<List<MbtiResult>> {
        return mbtiResultRepository.getAllResults()
    }
}