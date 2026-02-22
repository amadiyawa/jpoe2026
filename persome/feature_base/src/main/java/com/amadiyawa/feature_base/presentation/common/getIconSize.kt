package com.amadiyawa.feature_base.presentation.common

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.amadiyawa.feature_base.presentation.theme.AppDimensions
import com.amadiyawa.feature_base.presentation.theme.dimension

@Composable
fun getIconSize(): Dp {
    val dimension = MaterialTheme.dimension
    return if (dimension.orientation == AppDimensions.Orientation.LANDSCAPE) {
        // Paysage : icône plus petite pour laisser de la place au texte
        when (dimension.deviceType) {
            AppDimensions.DeviceType.LARGE_TABLET -> 160.dp
            AppDimensions.DeviceType.TABLET -> 130.dp
            else -> 100.dp
        }
    } else {
        // Portrait : grande icône centrale comme une illustration
        when (dimension.deviceType) {
            AppDimensions.DeviceType.LARGE_TABLET -> 280.dp
            AppDimensions.DeviceType.TABLET -> 240.dp
            else -> 200.dp
        }
    }
}