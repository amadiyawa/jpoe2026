package com.amadiyawa.feature_personnality.data.datasource.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.amadiyawa.feature_personnality.data.datasource.database.model.QuestionEntityModel
import com.amadiyawa.feature_personnality.domain.model.MbtiDimension

@Dao
internal interface QuestionDao {
    // Récupère toutes les questions
    @Query("SELECT * FROM questions")
    suspend fun getAllQuestions(): List<QuestionEntityModel>

    // Récupère les questions d'une dimension (ex: EI, SN...)
    @Query("SELECT * FROM questions WHERE dimension = :dimension")
    suspend fun getQuestionsByDimension(dimension: MbtiDimension): List<QuestionEntityModel>

    // Récupère une question par son id
    @Query("SELECT * FROM questions WHERE id = :id")
    suspend fun getQuestionById(id: Int): QuestionEntityModel?

    // Insère les questions (REPLACE pour éviter les doublons au rechargement)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(questions: List<QuestionEntityModel>)

    // Vérifie si des questions existent déjà en base
    @Query("SELECT COUNT(*) FROM questions")
    suspend fun getQuestionCount(): Int
}