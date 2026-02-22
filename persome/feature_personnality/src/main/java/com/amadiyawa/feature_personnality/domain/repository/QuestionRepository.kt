package com.amadiyawa.feature_personnality.domain.repository

import com.amadiyawa.feature_base.domain.result.OperationResult
import com.amadiyawa.feature_personnality.domain.model.MbtiDimension
import com.amadiyawa.feature_personnality.domain.model.Question

internal interface QuestionRepository {
    suspend fun getAllQuestions(): OperationResult<List<Question>>
    suspend fun getQuestionsByDimension(dimension: MbtiDimension): OperationResult<List<Question>>
    suspend fun getQuestionById(id: Int): OperationResult<Question>
    suspend fun insertQuestions(questionList: List<Question>)
}