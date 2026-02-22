package com.amadiyawa.feature_personnality.domain.usecase

import com.amadiyawa.feature_personnality.domain.model.MbtiDimension
import com.amadiyawa.feature_personnality.domain.model.Question

internal class CalculateMbtiTypeUseCase {

    // Reçoit les réponses sous forme Map<questionId, "A" ou "B">
    operator fun invoke(answers: Map<Int, String>, questions: List<Question>): String {

        // Compte les réponses A et B par dimension
        val scores = mutableMapOf(
            MbtiDimension.EI to Pair(0, 0), // Pair(countA, countB)
            MbtiDimension.SN to Pair(0, 0),
            MbtiDimension.TF to Pair(0, 0),
            MbtiDimension.JP to Pair(0, 0)
        )

        questions.forEach { question ->
            val answer = answers[question.id] ?: return@forEach
            val current = scores[question.dimension]!!

            scores[question.dimension] = if (answer == "A") {
                current.copy(first = current.first + 1)   // +1 pour A
            } else {
                current.copy(second = current.second + 1) // +1 pour B
            }
        }

        // Lettre majoritaire par dimension
        // EI : A = E, B = I
        // SN : A = S, B = N
        // TF : A = T, B = F
        // JP : A = J, B = P
        val e = if (scores[MbtiDimension.EI]!!.first >= scores[MbtiDimension.EI]!!.second) "E" else "I"
        val s = if (scores[MbtiDimension.SN]!!.first >= scores[MbtiDimension.SN]!!.second) "S" else "N"
        val t = if (scores[MbtiDimension.TF]!!.first >= scores[MbtiDimension.TF]!!.second) "T" else "F"
        val j = if (scores[MbtiDimension.JP]!!.first >= scores[MbtiDimension.JP]!!.second) "J" else "P"

        // Combine les 4 lettres → type final (ex: "INTJ")
        return "$e$s$t$j"
    }
}