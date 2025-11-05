package com.halimjr11.locaroo.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.halimjr11.locaroo.ui.model.PlaceUi
import com.halimjr11.locaroo.view.screens.MainScreen

fun NavGraphBuilder.mainNavGraph(
    onCardClick: (PlaceUi) -> Unit,
    onAddPlace: () -> Unit,
    onAboutClick: () -> Unit,
    onViewAllClick: (String) -> Unit,
    onFavoriteClick: () -> Unit,
) {
    composable(route = NavRoute.Main.route) {
        MainScreen(
            onCardClick = onCardClick,
            onAddPlace = onAddPlace,
            onAboutClick = onAboutClick,
            onViewAllClick = onViewAllClick,
            onFavoriteClick = onFavoriteClick,
        )
    }
}