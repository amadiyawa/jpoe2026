package com.amadiyawa.feature_personnality.data.datasource.database

import android.content.Context
import com.amadiyawa.feature_personnality.data.datasource.database.model.QuestionEntityModel
import com.amadiyawa.feature_personnality.domain.model.MbtiDimension
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import timber.log.Timber

// Modèle de désérialisation du JSON
@Serializable
private data class QuestionJson(
    val id: Int,
    val text: String,
    val optionA: String,
    val optionB: String,
    val dimension: String
)

internal class QuestionLocalDataSource(
    private val context: Context,
    private val questionDao: QuestionDao
) {

    // Charge les questions si la base est vide
    suspend fun loadQuestionsIfNeeded() {
        val count = questionDao.getQuestionCount()
        Timber.d("Questions en base : $count")

        if (count == 0) {
            Timber.d("Base vide → chargement depuis JSON")
            val questions = loadFromJson()
            questionDao.insertQuestions(questions)
            Timber.d("${questions.size} questions insérées")
        }
    }

    // Détecte la langue du téléphone et charge le bon fichier JSON
    private fun loadFromJson(): List<QuestionEntityModel> {
        val language = context.resources.configuration.locales[0].language
        val fileName = if (language == "fr") "questions_fr.json" else "questions_en.json"
        Timber.d("Langue détectée : $language → fichier : $fileName")

        return try {
            val jsonString = context.assets.open(fileName)
                .bufferedReader()
                .use { it.readText() }

            Json.decodeFromString<List<QuestionJson>>(jsonString).map { q ->
                QuestionEntityModel(
                    id = q.id,
                    text = q.text,
                    optionA = q.optionA,
                    optionB = q.optionB,
                    dimension = MbtiDimension.valueOf(q.dimension)
                )
            }
        } catch (e: Exception) {
            Timber.e(e, "Erreur chargement JSON : $fileName")
            emptyList()
        }
    }
}