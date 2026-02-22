package com.amadiyawa.feature_personnality.domain.usecase

import com.amadiyawa.feature_base.domain.result.OperationResult
import com.amadiyawa.feature_personnality.domain.model.Question
import com.amadiyawa.feature_personnality.domain.repository.QuestionRepository

internal class GetQuestionsUseCase(
    private val questionRepository: QuestionRepository
) {
    suspend operator fun invoke(): OperationResult<List<Question>> {
        return questionRepository.getAllQuestions()
    }
}