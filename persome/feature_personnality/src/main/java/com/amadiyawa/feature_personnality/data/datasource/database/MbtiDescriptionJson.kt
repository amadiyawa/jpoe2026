package com.amadiyawa.feature_personnality.data.datasource.database

import kotlinx.serialization.Serializable

@Serializable
internal data class MbtiDescriptionJson(
    val type: String,
    val whoYouAre: String,
    val strengths: String,
    val growthAreas: String,
    val careers: String
)