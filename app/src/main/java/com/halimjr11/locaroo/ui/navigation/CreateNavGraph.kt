package com.halimjr11.locaroo.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.halimjr11.locaroo.view.screens.create.CreateScreen

fun NavGraphBuilder.createNavGraph(navController: NavHostController) {
    composable(route = NavRoute.Create.route) {
        CreateScreen(navController = navController)
    }
}
