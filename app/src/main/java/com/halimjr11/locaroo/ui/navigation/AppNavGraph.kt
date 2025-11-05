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
        startDestination = NavRoute.Splash.route,
        modifier = modifier
    ) {
        splashNavGraph(navController)
        authNavGraph(navController)
        homeNavGraph(
            action = {
                navController.navigate("detail/${it.id}")
            },
            onAddPlace = {
                navController.navigate(NavRoute.Create.route)
            }
        )
        searchNavGraph(
            onBack = { navController.popBackStack() },
            onPlaceClick = { navController.navigate("detail/${it.id}") }
        )
        journeyNavGraph()
        favoriteNavGraph(
            onPlaceClick = { navController.navigate("detail/${it.id}") }
        )
        detailNavGraph {
            navController.popBackStack()
        }
        createNavGraph(navController)
    }
}
