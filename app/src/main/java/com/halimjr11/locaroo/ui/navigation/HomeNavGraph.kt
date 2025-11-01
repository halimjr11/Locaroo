package com.halimjr11.locaroo.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.halimjr11.locaroo.ui.model.PlaceUi
import com.halimjr11.locaroo.view.screens.home.HomeScreen

fun NavGraphBuilder.homeNavGraph(action: (PlaceUi) -> Unit) {
    composable(route = NavRoute.Home.route) {
        HomeScreen(onCardClick = action)
    }
}
