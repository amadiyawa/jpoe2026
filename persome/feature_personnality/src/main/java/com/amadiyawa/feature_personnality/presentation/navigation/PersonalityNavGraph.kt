package com.amadiyawa.feature_personnality.presentation.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.amadiyawa.feature_personnality.presentation.screen.history.HistoryScreen
import com.amadiyawa.feature_personnality.presentation.screen.questionnaire.QuestionnaireScreen
import com.amadiyawa.feature_personnality.presentation.screen.result.ResultScreen
import com.amadiyawa.feature_personnality.presentation.screen.userinfo.UserInfoScreen
import timber.log.Timber

// Durée des animations en millisecondes
private const val ANIM_DURATION = 400

fun NavGraphBuilder.personalityGraph(navController: NavHostController) {
    navigation(
        startDestination = PersonalityRoutes.HISTORY,
        route = PersonalityRoutes.GRAPH
    ) {

        // ─── ÉCRAN 1 : HISTORIQUE (accueil) ───────────────────
        composable(route = PersonalityRoutes.HISTORY) {
            Timber.d("Affichage HistoryScreen")
            HistoryScreen(
                // Bouton "Commencer le test"
                onStartTest = {
                    navController.navigate(PersonalityRoutes.QUESTIONNAIRE)
                },
                // Click sur un résultat de l'historique
                onResultClick = { mbtiType ->
                    navController.navigate(PersonalityRoutes.resultRoute(mbtiType))
                }
            )
        }

        // ─── ÉCRAN 2 : QUESTIONNAIRE (30 questions) ───────────
        composable(
            route = PersonalityRoutes.QUESTIONNAIRE,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { 1000 },
                    animationSpec = tween(ANIM_DURATION)
                )
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { 1000 },
                    animationSpec = tween(ANIM_DURATION)
                )
            }
        ) {
            Timber.d("Affichage QuestionnaireScreen")
            QuestionnaireScreen(
                onQuestionnaireComplete = { mbtiType ->
                    navController.navigate(PersonalityRoutes.userInfoRoute(mbtiType)) // ← ajout
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        // ─── ÉCRAN 3 : FORMULAIRE UTILISATEUR ─────────────────
        composable(
            route = "${PersonalityRoutes.USER_INFO}/{mbtiType}",
            arguments = listOf(
                navArgument("mbtiType") { type = NavType.StringType }
            ),
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { 1000 },
                    animationSpec = tween(ANIM_DURATION)
                )
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { 1000 },
                    animationSpec = tween(ANIM_DURATION)
                )
            }
        ) {backStackEntry ->
            val mbtiType = backStackEntry.arguments?.getString("mbtiType")
                ?: return@composable

            Timber.d("Affichage UserInfoScreen - type: $mbtiType")
            UserInfoScreen(
                mbtiType = mbtiType,
                onResultReady = { type ->
                    navController.navigate(PersonalityRoutes.resultRoute(type)) {
                        popUpTo(PersonalityRoutes.QUESTIONNAIRE) { inclusive = true }
                    }
                }
            )
        }

        // ─── ÉCRAN 4 : RÉSULTAT MBTI ──────────────────────────
        composable(
            route = "${PersonalityRoutes.RESULT}/{mbtiType}",
            arguments = listOf(
                navArgument("mbtiType") { type = NavType.StringType }
            ),
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { 1000 },
                    animationSpec = tween(ANIM_DURATION)
                )
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { 1000 },
                    animationSpec = tween(ANIM_DURATION)
                )
            }
        ) { backStackEntry ->
            // Récupère le type MBTI passé en paramètre (ex: "INTJ")
            val mbtiType = backStackEntry.arguments?.getString("mbtiType")
                ?: return@composable

            Timber.d("Affichage ResultScreen - type: $mbtiType")
            ResultScreen(
                mbtiType = mbtiType,
                // Recommencer le test depuis le début
                onRetakeTest = {
                    navController.navigate(PersonalityRoutes.QUESTIONNAIRE) {
                        popUpTo(PersonalityRoutes.HISTORY)
                    }
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}