package com.amadiyawa.feature_personnality.domain.model

internal data class Question(
    val id: Int,
    val text: String,
    val optionA: String,
    val optionB: String,
    val dimension: MbtiDimension  // Quelle dimension cette question mesure
)

// Les 4 dimensions du MBTI
enum class MbtiDimension {
    EI,  // Extraversion / Introversion      (Q1-Q10)
    SN,  // Sensation / Intuition            (Q11-Q17)
    TF,  // Pensée (Thinking) / Sentiment    (Q18-Q21)
    JP   // Jugement / Perception            (Q22-Q30)
}