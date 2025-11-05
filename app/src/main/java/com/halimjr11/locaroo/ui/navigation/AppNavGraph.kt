package com.halimjr11.locaroo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.halimjr11.locaroo.ui.model.PlaceUi

@Composable
fun AppNavGraph(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        val actionPlace: (PlaceUi) -> Unit = {
            navController.navigate("detail/${it.id}")
        }
        mainNavGraph(
            onCardClick = actionPlace,
            onAddPlace = {
                navController.navigate(NavRoute.Create.route)
            },
            onAboutClick = {
                navController.navigate(NavRoute.About.route)
            },
            onViewAllClick = { encoded ->
                navController.navigate("view_all/$encoded")
            },
            onFavoriteClick = {
                navController.navigate(NavRoute.Favorite.route)
            }
        )
        aboutNavGraph()
        authNavGraph(navController)
        favoriteNavGraph(
            onPlaceClick = actionPlace
        )
        detailNavGraph {
            navController.popBackStack()
        }
        createNavGraph(navController)
        viewAllNavGraph(
            action = actionPlace,
            onBack = {
                navController.popBackStack()
            }
        )
    }
}
