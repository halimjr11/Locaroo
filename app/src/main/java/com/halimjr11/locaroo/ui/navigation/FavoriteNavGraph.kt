package com.halimjr11.locaroo.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.halimjr11.locaroo.ui.model.PlaceUi
import com.halimjr11.locaroo.view.screens.favorite.FavoriteScreen

fun NavGraphBuilder.favoriteNavGraph(
    onPlaceClick: (PlaceUi) -> Unit = {}
) {
    composable(route = NavRoute.Favorite.route) {
        FavoriteScreen(onCardClick = onPlaceClick)
    }
}
