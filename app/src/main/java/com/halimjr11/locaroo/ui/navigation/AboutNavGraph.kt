package com.halimjr11.locaroo.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.halimjr11.locaroo.view.screens.about.AboutScreen

fun NavGraphBuilder.aboutNavGraph() {
    composable(route = NavRoute.About.route) {
        AboutScreen()
    }
}