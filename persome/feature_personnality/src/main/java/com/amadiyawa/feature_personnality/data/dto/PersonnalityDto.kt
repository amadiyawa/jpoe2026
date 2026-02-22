package com.amadiyawa.feature_personnality.data.dto

import kotlinx.serialization.Serializable

// Ce qu'on envoie au backend
@Serializable
internal data class AiDescriptionRequest(
    val mbtiType: String,       // ex: "INTJ"
    val firstName: String,      // ex: "Jean"
    val age: Int,               // ex: 20
    val city: String,           // ex: "Yaoundé"
    val situation: String       // ex: "STUDENT"
)

// Ce que le backend retourne
@Serializable
internal data class AiDescriptionResponse(
    val mbtiType: String,
    val description: String     // Texte généré par Gemini (~200 mots)
)