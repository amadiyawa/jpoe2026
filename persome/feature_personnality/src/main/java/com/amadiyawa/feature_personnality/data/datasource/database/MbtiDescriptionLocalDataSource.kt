package com.amadiyawa.feature_personnality.data.datasource.database

import android.content.Context
import kotlinx.serialization.json.Json
import timber.log.Timber

internal class MbtiDescriptionLocalDataSource(
    private val context: Context
) {
    // Cache en mémoire pour éviter de relire le fichier à chaque appel
    private var descriptionsCache: Map<String, MbtiDescriptionJson>? = null

    // Retourne la description statique pour un type MBTI donné
    fun getDescription(mbtiType: String): MbtiDescriptionJson? {
        if (descriptionsCache == null) {
            descriptionsCache = loadFromJson()
        }
        return descriptionsCache?.get(mbtiType)
    }

    // Formate la description complète en texte lisible
    fun getFormattedDescription(mbtiType: String): String {
        val desc = getDescription(mbtiType) ?: return ""

        return buildString {
            append("👤 Qui tu es\n")
            append(desc.whoYouAre)
            append("\n\n")
            append("💪 Tes forces\n")
            append(desc.strengths)
            append("\n\n")
            append("🌱 Tes axes de développement\n")
            append(desc.growthAreas)
            append("\n\n")
            append("🎯 Carrières au Cameroun\n")
            append(desc.careers)
        }
    }

    private fun loadFromJson(): Map<String, MbtiDescriptionJson> {
        val language = context.resources.configuration.locales[0].language
        val fileName = if (language == "fr") {
            "mbti_descriptions_fr.json"
        } else {
            "mbti_descriptions_en.json"
        }

        Timber.d("Chargement descriptions MBTI : $fileName")

        return try {
            val jsonString = context.assets.open(fileName)
                .bufferedReader()
                .use { it.readText() }

            Json.decodeFromString<List<MbtiDescriptionJson>>(jsonString)
                .associateBy { it.type } // Map<"INTJ", MbtiDescriptionJson>
        } catch (e: Exception) {
            Timber.e(e, "Erreur chargement descriptions MBTI")
            emptyMap()
        }
    }
}