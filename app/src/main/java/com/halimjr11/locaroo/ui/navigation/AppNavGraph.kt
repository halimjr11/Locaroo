package com.halimjr11.locaroo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.google.gson.Gson
import com.halimjr11.locaroo.ui.model.PlaceUi
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

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
        val actionPlace: (PlaceUi) -> Unit = {
            navController.navigate("detail/${it.id}")
        }
        splashNavGraph(navController)
        authNavGraph(navController)
        homeNavGraph(
            action = actionPlace,
            onAddPlace = {
                navController.navigate(NavRoute.Create.route)
            },
            onAboutClick = {
                navController.navigate(NavRoute.About.route)
            },
            onViewAllClick = {
                val places = Gson().toJson(it)
                val encoded = URLEncoder.encode(places, StandardCharsets.UTF_8.toString())
                navController.navigate("view_all/$encoded")
            },
            onFavoriteClick = {
                navController.navigate(NavRoute.Favorite.route)
            },
            onBack = {
                navController.popBackStack()
            }
        )
        searchNavGraph(
            onBack = { navController.popBackStack() },
            onPlaceClick = actionPlace
        )
        journeyNavGraph()
        favoriteNavGraph(
            onPlaceClick = actionPlace
        )
        detailNavGraph {
            navController.popBackStack()
        }
        createNavGraph(navController)
    }
}
