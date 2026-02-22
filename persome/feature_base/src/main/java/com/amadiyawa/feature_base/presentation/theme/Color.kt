package com.amadiyawa.feature_base.presentation.theme

import androidx.compose.ui.graphics.Color
import com.amadiyawa.feature_base.common.util.toColor

/**
 * Interface representing custom colors used in the application.
 *
 * @property success The color used to indicate success.
 * @property warning The color used to indicate a warning.
 * @property info The color used to indicate informational messages.
 * @property danger The color used to indicate danger or errors.
 * @property onSuccess The color used for text or icons on success backgrounds.
 * @property onWarning The color used for text or icons on warning backgrounds.
 * @property onInfo The color used for text or icons on informational backgrounds.
 * @property onDanger The color used for text or icons on danger backgrounds.
 * @author Amadou Iyawa
 */
interface CustomColor {
    val success: Color
    val warning: Color
    val info: Color
    val danger: Color
    val onSuccess: Color
    val onWarning: Color
    val onInfo: Color
    val onDanger: Color
}

internal const val WHITE = "#FFFFFF"
internal const val BLACK = "#000000"

// ─── LIGHT THEME ───────────────────────────────────────────
val LightPrimary = Color(0xFF5C35B0)          // Violet profond
val LightPrimaryVariant = Color(0xFFEDE7F6)   // Violet très clair (hover, selected)
val LightSecondary = Color(0xFF7E57C2)        // Violet moyen
val LightSecondaryVariant = Color(0xFFD1C4E9) // Lavande clair
val LightTertiary = Color(0xFFAB47BC)         // Mauve / Orchidée
val LightTertiaryContainer = Color(0xFFF8D7FD)// Mauve très clair
val LightSurface = Color(0xFFFFFFFF)
val LightSurfaceContainer = Color(0xFFF3EFF7) // Blanc violacé
val LightSurfaceVariant = Color(0xFFE8E0F0)
val LightSurfaceContainerHigh = Color(0xFFEDE7F6)
val LightSurfaceContainerLow = Color(0xFFF7F3FB)
val LightBackground = Color(0xFFF5F0FF)       // Fond légèrement violacé
val LightError = Color(0xFFB00020)
val LightOnPrimary = Color(0xFFFFFFFF)
val LightOnPrimaryContainer = Color(0xFF4527A0)
val LightOnSecondary = Color(0xFFFFFFFF)
val LightOnSecondaryContainer = Color(0xFF311B92)
val LightOnTertiary = Color(0xFFFFFFFF)
val LightOnTertiaryContainer = Color(0xFF6A1B9A)
val LightOnSurface = Color(0xFF1C1B1F)
val LightOnSurfaceVariant = Color(0xFF6B5E82) // Gris violacé
val LightOnBackground = Color(0xFF1C1B1F)
val LightOnError = Color(0xFFFFFFFF)
val LightOutline = Color(0xFFBBB0CC)          // Lilas gris

// ─── DARK THEME ────────────────────────────────────────────
val DarkPrimary = Color(0xFFCE9FFC)           // Violet clair lumineux
val DarkPrimaryContainer = Color(0xFF4A2E8F)  // Violet sombre
val DarkSecondary = Color(0xFFB39DDB)         // Lavande douce
val DarkSecondaryContainer = Color(0xFF311B92)// Indigo sombre
val DarkTertiary = Color(0xFFCE93D8)          // Mauve clair
val DarkTertiaryContainer = Color(0xFF6A1B9A) // Mauve sombre
val DarkSurface = Color(0xFF1A1625)           // Très sombre, légèrement violet
val DarkSurfaceVariant = Color(0xFF26203A)
val DarkSurfaceContainer = Color(0xFF221D33)
val DarkSurfaceContainerHigh = Color(0xFF2C2640)
val DarkSurfaceContainerLow = Color(0xFF1A1625)
val DarkBackground = Color(0xFF120F1E)        // Fond quasi-noir violacé
val DarkError = Color(0xFFCF6679)
val DarkOnPrimary = Color(0xFF1A0050)
val DarkOnPrimaryContainer = Color(0xFFE9D5FF)
val DarkOnSecondary = Color(0xFF1A0050)
val DarkOnSecondaryContainer = Color(0xFFD1C4E9)
val DarkOnTertiary = Color(0xFF2D0040)
val DarkOnTertiaryContainer = Color(0xFFF3D1F8)
val DarkOnSurface = Color(0xFFEDE8F5)         // Blanc cassé violacé
val DarkOnSurfaceVariant = Color(0xFFCFC3E4)
val DarkOnBackground = Color(0xFFEDE8F5)
val DarkOnError = Color(0xFF000000)
val DarkOutline = Color(0xFF7B6F94)

/**
 * Specifies the custom light's colours that should be used through the application in a non-graphic
 * library specific amount.
 *
 * @author Amadou Iyawa
 */
data object CustomLightColorDefaults {
    internal const val SUCCESS = "#4CAF50"
    internal const val WARNING = "#FFC107"
    internal const val INFO = "#2196F3"
    internal const val DANGER = "#F44336"
    internal const val ONSUCCESS = WHITE
    internal const val ONWARNING = "#000000"
    internal const val ONINFO = WHITE
    internal const val ONDANGER = WHITE
}

/**
 * Specifies the custom dark's colours that should be used through the application in a non-graphic
 * library specific amount.
 *
 * @author Amadou Iyawa
 */
data object CustomDarkColorDefaults {
    internal const val SUCCESS = "#81C784"
    internal const val WARNING = "#FFD54F"
    internal const val INFO = "#64B5F6"
    internal const val DANGER = "#E57373"
    internal const val ONSUCCESS = BLACK
    internal const val ONWARNING = BLACK
    internal const val ONINFO = BLACK
    internal const val ONDANGER = BLACK
}

/**
 * Specifies the accent's colours that should be used through the application in a non-graphic
 * library specific amount.
 *
 * @author Amadou Iyawa
 */
data object AccentColorDefaults {
    internal const val PRIMARY = "#2196F3"
    internal const val WARNING = "#FFC107"
    internal const val NEUTRAL = "#808080"
    internal const val BACKGROUND = "#2F3135"
    internal const val SUCCESS = "#98FB98"
    internal const val ERROR = "#FF6961"
    internal const val LIGHT = "#87CEFA"
    internal const val BRIGHT = "#00BFFF"
}

/**
 * Data class representing custom light colors used in the application.
 *
 * @property success The color used to indicate success.
 * @property warning The color used to indicate a warning.
 * @property info The color used to indicate informational messages.
 * @property danger The color used to indicate danger or errors.
 * @property onSuccess The color used for text or icons on success backgrounds.
 * @property onWarning The color used for text or icons on warning backgrounds.
 * @property onInfo The color used for text or icons on informational backgrounds.
 * @property onDanger The color used for text or icons on danger backgrounds.
 * @author Amadou Iyawa
 */
data class CustomLightColor(
    override val success: Color = CustomLightColorDefaults.SUCCESS.toColor(),
    override val warning: Color = CustomLightColorDefaults.WARNING.toColor(),
    override val info: Color = CustomLightColorDefaults.INFO.toColor(),
    override val danger: Color = CustomLightColorDefaults.DANGER.toColor(),
    override val onSuccess: Color = CustomLightColorDefaults.ONSUCCESS.toColor(),
    override val onWarning: Color = CustomLightColorDefaults.ONWARNING.toColor(),
    override val onInfo: Color = CustomLightColorDefaults.ONINFO.toColor(),
    override val onDanger: Color = CustomLightColorDefaults.ONDANGER.toColor()
) : CustomColor

/**
 * Data class representing custom dark colors used in the application.
 *
 * @property success The color used to indicate success.
 * @property warning The color used to indicate a warning.
 * @property info The color used to indicate informational messages.
 * @property danger The color used to indicate danger or errors.
 * @property onSuccess The color used for text or icons on success backgrounds.
 * @property onWarning The color used for text or icons on warning backgrounds.
 * @property onInfo The color used for text or icons on informational backgrounds.
 * @property onDanger The color used for text or icons on danger backgrounds.
 * @author Amadou Iyawa
 */
data class CustomDarkColor(
    override val success: Color = CustomDarkColorDefaults.SUCCESS.toColor(),
    override val warning: Color = CustomDarkColorDefaults.WARNING.toColor(),
    override val info: Color = CustomDarkColorDefaults.INFO.toColor(),
    override val danger: Color = CustomDarkColorDefaults.DANGER.toColor(),
    override val onSuccess: Color = CustomDarkColorDefaults.ONSUCCESS.toColor(),
    override val onWarning: Color = CustomDarkColorDefaults.ONWARNING.toColor(),
    override val onInfo: Color = CustomDarkColorDefaults.ONINFO.toColor(),
    override val onDanger: Color = CustomDarkColorDefaults.ONDANGER.toColor()
) : CustomColor

/**
 * Data class representing accent colors used in the application.
 *
 * @property primary The primary accent color.
 * @property warning The color used to indicate warnings.
 * @property neutral The neutral color.
 * @property background The background color.
 * @property success The color used to indicate success.
 * @property error The color used to indicate errors.
 * @property light The light accent color.
 * @property bright The bright accent color.
 * @author Amadou Iyawa
 */
data class AccentColor(
    val primary: Color = AccentColorDefaults.PRIMARY.toColor(),
    val warning: Color = AccentColorDefaults.WARNING.toColor(),
    val neutral: Color = AccentColorDefaults.NEUTRAL.toColor(),
    val background: Color = AccentColorDefaults.BACKGROUND.toColor(),
    val success: Color = AccentColorDefaults.SUCCESS.toColor(),
    val error: Color = AccentColorDefaults.ERROR.toColor(),
    val light: Color = AccentColorDefaults.LIGHT.toColor(),
    val bright: Color = AccentColorDefaults.BRIGHT.toColor()
)