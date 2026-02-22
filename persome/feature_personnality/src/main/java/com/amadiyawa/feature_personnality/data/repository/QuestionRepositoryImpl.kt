package com.amadiyawa.feature_personnality.data.repository

import com.amadiyawa.feature_base.domain.result.OperationResult
import com.amadiyawa.feature_personnality.data.datasource.database.QuestionDao
import com.amadiyawa.feature_personnality.data.datasource.database.QuestionLocalDataSource
import com.amadiyawa.feature_personnality.data.datasource.database.model.toQuestion
import com.amadiyawa.feature_personnality.domain.model.MbtiDimension
import com.amadiyawa.feature_personnality.domain.model.Question
import com.amadiyawa.feature_personnality.domain.repository.QuestionRepository
import timber.log.Timber

internal class QuestionRepositoryImpl(
    private val questionDao: QuestionDao,
    private val questionLocalDataSource: QuestionLocalDataSource
) : QuestionRepository {

    override suspend fun getAllQuestions(): OperationResult<List<Question>> {
        return try {
            // Charge depuis JSON si la base est vide
            questionLocalDataSource.loadQuestionsIfNeeded()

            val questions = questionDao.getAllQuestions().map { it.toQuestion() }
            Timber.d("${questions.size} questions récupérées")
            OperationResult.success(questions)
        } catch (e: Exception) {
            Timber.e(e, "Erreur récupération questions")
            OperationResult.error(e)
        }
    }

    override suspend fun getQuestionsByDimension(dimension: MbtiDimension): OperationResult<List<Question>> {
        return try {
            val questions = questionDao.getQuestionsByDimension(dimension).map { it.toQuestion() }
            Timber.d("${questions.size} questions pour la dimension $dimension")
            OperationResult.success(questions)
        } catch (e: Exception) {
            Timber.e(e, "Erreur récupération questions par dimension")
            OperationResult.error(e)
        }
    }

    override suspend fun getQuestionById(id: Int): OperationResult<Question> {
        return try {
            val question = questionDao.getQuestionById(id)?.toQuestion()
                ?: return OperationResult.error(Exception("Question $id introuvable"))
            OperationResult.success(question)
        } catch (e: Exception) {
            Timber.e(e, "Erreur récupération question $id")
            OperationResult.error(e)
        }
    }

    override suspend fun insertQuestions(questionList: List<Question>) {
        // Non utilisé depuis l'extérieur — géré par QuestionLocalDataSource
    }
}