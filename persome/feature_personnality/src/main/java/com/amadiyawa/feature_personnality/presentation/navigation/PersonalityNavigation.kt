package com.amadiyawa.feature_personnality.presentation.navigation

// Toutes les routes de l'application en un seul endroit
object PersonalityRoutes {
    const val GRAPH = "personality_graph"
    const val HISTORY = "history"
    const val QUESTIONNAIRE = "questionnaire"
    const val USER_INFO = "user_info"
    const val RESULT = "result"

    // Route complète vers un résultat (avec l'id)
    fun resultRoute(mbtiType: String) = "$RESULT/$mbtiType"
    fun userInfoRoute(mbtiType: String) = "$USER_INFO/$mbtiType"
}