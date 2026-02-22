package com.amadiyawa.feature_personnality.data.datasource.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.amadiyawa.feature_personnality.data.datasource.database.model.MbtiResultEntityModel

@Dao
internal interface MbtiResultDao {

    // Récupère tous les résultats (du plus récent au plus ancien)
    @Query("SELECT * FROM mbti_results ORDER BY createdDate DESC")
    suspend fun getAllResults(): List<MbtiResultEntityModel>

    // Récupère un résultat par son id
    @Query("SELECT * FROM mbti_results WHERE id = :id")
    suspend fun getResultById(id: Int): MbtiResultEntityModel?

    // Sauvegarde un résultat
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResult(result: MbtiResultEntityModel)
}