package com.amadiyawa.feature_personnality.presentation.util

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun SetupPersonalitySystemBars() {
    val systemUiController = rememberSystemUiController()
    val primary = MaterialTheme.colorScheme.primary

    SideEffect {
        systemUiController.setStatusBarColor(
            color = primary,
            darkIcons = false  // icônes blanches sur fond violet
        )
        systemUiController.setNavigationBarColor(
            color = primary,
            darkIcons = false
        )
    }
}