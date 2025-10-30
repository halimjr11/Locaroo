package com.halimjr11.locaroo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = NavRoute.Home.route,
        modifier = modifier
    ) {
        homeNavGraph()
        searchNavGraph(
            onBack = { navController.popBackStack() },
            onPlaceClick = { navController.navigate("detail/${it.id}") }
        )
        journeyNavGraph()
    }
}
