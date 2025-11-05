package com.halimjr11.locaroo.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.halimjr11.locaroo.view.screens.splash.SplashScreen

fun NavGraphBuilder.splashNavGraph(navController: NavHostController) {
    composable(route = NavRoute.Splash.route) {
        SplashScreen(
            navController = navController
        )
    }
}