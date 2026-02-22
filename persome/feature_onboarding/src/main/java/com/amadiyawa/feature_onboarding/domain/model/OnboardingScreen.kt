package com.amadiyawa.feature_onboarding.domain.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material.icons.filled.Stars
import kotlinx.serialization.Serializable

/**
 * Data class representing an onboarding item.
 *
 * @property id The unique identifier for the onboarding item.
 * @property titleResId The resource ID for the title of the onboarding item.
 * @property descriptionResId The resource ID for the description of the onboarding item.
 * @property imageResId The resource ID for the image of the onboarding item.
 */
@Serializable
data class OnboardingScreen(
    val id: String,
    val titleResId: Int,
    val descriptionResId: Int,
    val imageResId: Int? = null,
    val iconType: IconType? = null,
    val requiresPermissions: Boolean = false,
    val permissions: List<String> = emptyList()
)

enum class IconType {
    PSYCHOLOGY,
    QUESTION_ANSWER,
    STARS,
    PLAY_ARROW
}

// Extension pour convertir IconType → ImageVector
fun IconType.toImageVector() = when (this) {
    IconType.PSYCHOLOGY -> Icons.Default.Psychology
    IconType.QUESTION_ANSWER -> Icons.Default.QuestionAnswer
    IconType.STARS -> Icons.Default.Stars
    IconType.PLAY_ARROW -> Icons.Default.PlayArrow
}