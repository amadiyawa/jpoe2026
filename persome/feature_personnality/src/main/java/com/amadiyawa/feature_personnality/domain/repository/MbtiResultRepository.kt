package com.amadiyawa.feature_personnality.domain.repository

import com.amadiyawa.feature_base.domain.result.OperationResult
import com.amadiyawa.feature_personnality.domain.model.MbtiResult

internal interface MbtiResultRepository {
    suspend fun getAllResults(): OperationResult<List<MbtiResult>>
    suspend fun getResultById(id: Int): OperationResult<MbtiResult>
    suspend fun insertResult(result: MbtiResult)
}