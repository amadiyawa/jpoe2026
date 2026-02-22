package com.amadiyawa.feature_personnality.domain.model

internal data class MbtiResult(
    val id: Int? = null,
    val userInfo: UserInfo,
    val mbtiType: String,
    val aiDescription: String?,      // Peut être null si pas d'internet
    val staticDescription: String,   // Toujours présent (rédigé par l'équipe Contenu)
    val createdDate: Long = System.currentTimeMillis()
)