package com.amadiyawa.feature_personnality.domain.model

import kotlinx.serialization.Serializable

@Serializable
internal data class UserInfo(
    val id: Int? = null,
    val firstName: String,
    val age: Int,
    val city: String,
    val situation: UserSituation,
    val createdDate: Long = System.currentTimeMillis()
)

enum class UserSituation {
    STUDENT,        // Étudiant
    EMPLOYED,       // En emploi
    SELF_EMPLOYED,  // Indépendant / Entrepreneur
    SEEKING         // En recherche d'emploi
}