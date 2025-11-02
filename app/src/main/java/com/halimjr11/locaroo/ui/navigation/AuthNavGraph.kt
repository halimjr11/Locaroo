package com.halimjr11.locaroo.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.halimjr11.locaroo.view.screens.auth.LoginScreen
import com.halimjr11.locaroo.view.screens.auth.RegisterScreen

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    composable(route = NavRoute.Login.route) {
        LoginScreen(
            onNavigateToRegister = {
                navController.navigate(NavRoute.Register.route) {
                    popUpTo(NavRoute.Login.route) { inclusive = true }
                }
            },
            onNavigateToHome = {
                navController.navigate(NavRoute.Home.route) {
                    popUpTo(NavRoute.Login.route) { inclusive = true }
                }
            }
        )
    }

    composable(route = NavRoute.Register.route) {
        RegisterScreen(
            onNavigateToLogin = {
                navController.navigate(NavRoute.Login.route) {
                    popUpTo(NavRoute.Register.route) { inclusive = true }
                }
            }
        )
    }
}

