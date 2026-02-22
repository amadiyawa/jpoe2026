package com.amadiyawa.feature_personnality.data.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.amadiyawa.feature_personnality.data.datasource.database.model.MbtiResultEntityModel
import com.amadiyawa.feature_personnality.data.datasource.database.model.QuestionEntityModel

@Database(
    entities = [
        QuestionEntityModel::class,
        MbtiResultEntityModel::class
    ],
    version = 1,
    exportSchema = false
)
internal abstract class PersonalityDatabase : RoomDatabase() {
    abstract fun questionDao(): QuestionDao
    abstract fun mbtiResultDao(): MbtiResultDao
}