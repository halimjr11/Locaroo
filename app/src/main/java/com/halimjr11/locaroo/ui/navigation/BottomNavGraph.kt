package com.halimjr11.locaroo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.halimjr11.locaroo.ui.model.PlaceUi

@Composable
fun BottomNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    onCardClick: (PlaceUi) -> Unit,
    onAddPlace: () -> Unit,
    onAboutClick: () -> Unit,
    onViewAllClick: (String) -> Unit,
    onFavoriteClick: () -> Unit,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = NavRoute.Home.route
    ) {
        homeNavGraph(
            action = onCardClick,
            onAddPlace = onAddPlace,
            onAboutClick = onAboutClick,
            onViewAllClick = onViewAllClick,
            onFavoriteClick = onFavoriteClick
        )
        searchNavGraph(
            onBack = { navController.popBackStack() },
            onPlaceClick = onCardClick
        )
        journeyNavGraph()
    }
}