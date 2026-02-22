package com.amadiyawa.feature_personnality.data.datasource.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.amadiyawa.feature_personnality.domain.model.MbtiDimension
import com.amadiyawa.feature_personnality.domain.model.Question

@Entity(tableName = "questions")
internal data class QuestionEntityModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val text: String,
    val optionA: String,
    val optionB: String,
    val dimension: MbtiDimension
)

// Entity → Domain
internal fun QuestionEntityModel.toQuestion() = Question(
    id = id,
    text = text,
    optionA = optionA,
    optionB = optionB,
    dimension = dimension
)

// Domain → Entity
internal fun Question.toQuestionEntityModel() = QuestionEntityModel(
    text = text,
    optionA = optionA,
    optionB = optionB,
    dimension = dimension
)