package app.keelo.gateway.presentation.screen.appentry

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.amadiyawa.feature_base.presentation.compose.composable.SetupSystemBars
import com.amadiyawa.feature_base.presentation.navigation.AppRoutes
import com.amadiyawa.feature_base.presentation.navigation.AppState
import com.amadiyawa.feature_base.presentation.navigation.rememberAppState
import com.amadiyawa.feature_base.presentation.theme.AppTheme
import com.amadiyawa.feature_onboarding.presentation.navigation.onboardingGraph
import com.amadiyawa.feature_personnality.presentation.navigation.PersonalityRoutes
import com.amadiyawa.feature_personnality.presentation.navigation.personalityGraph

/**
 * Point d'entrée principal de l'application.
 * Flow : Onboarding → Personality (History → Questionnaire → UserInfo → Result)
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MainScreen(
    windowSizeClass: WindowSizeClass,
    appState: AppState = rememberAppState(windowSizeClass = windowSizeClass)
) {
    AppTheme {
        SetupSystemBars()
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground,
        ) { paddingValues ->
            NavHost(
                navController = appState.navController,
                startDestination = AppRoutes.ONBOARDING_GRAPH,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues) // ← insets gérés ici une seule fois
            ) {
                onboardingGraph(appState.navController)
                navigation(route = AppRoutes.AUTH_GRAPH, startDestination = "auth_redirect") {
                    composable("auth_redirect") {
                        LaunchedEffect(Unit) {
                            appState.navController.navigate(PersonalityRoutes.GRAPH) {
                                popUpTo(AppRoutes.AUTH_GRAPH) { inclusive = true }
                            }
                        }
                    }
                }
                personalityGraph(appState.navController)
            }
        }
    }
}