package com.halimjr11.locaroo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.halimjr11.locaroo.ui.navigation.NavRoute
import com.halimjr11.locaroo.ui.screens.home.HomeScreen
import com.halimjr11.locaroo.ui.screens.search.SearchScreen
import com.halimjr11.locaroo.ui.screens.journey.JourneyScreen

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
        composable(NavRoute.Home.route) {
            HomeScreen()
        }
        composable(NavRoute.Search.route) {
            SearchScreen()
        }
        composable(NavRoute.Journey.route) {
            JourneyScreen()
        }
    }
}
