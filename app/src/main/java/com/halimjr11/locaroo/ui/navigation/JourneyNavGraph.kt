package com.halimjr11.locaroo.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.halimjr11.locaroo.view.screens.journey.JourneyScreen

fun NavGraphBuilder.journeyNavGraph() {
    composable(route = NavRoute.Journey.route) {
        JourneyScreen()
    }
}
