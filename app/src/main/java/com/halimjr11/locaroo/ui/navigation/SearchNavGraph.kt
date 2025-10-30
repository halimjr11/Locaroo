package com.halimjr11.locaroo.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.halimjr11.locaroo.ui.model.PlaceUi
import com.halimjr11.locaroo.view.screens.search.SearchScreen

fun NavGraphBuilder.searchNavGraph(
    onBack: () -> Unit = {},
    onPlaceClick: (PlaceUi) -> Unit = {}
) {
    composable(route = NavRoute.Search.route) {
        SearchScreen(
            onBack = onBack,
            onPlaceClick = onPlaceClick
        )
    }
}
