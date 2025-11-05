package com.halimjr11.locaroo.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.halimjr11.locaroo.ui.model.PlaceUi
import com.halimjr11.locaroo.view.screens.home.ViewAllScreen
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

fun NavGraphBuilder.viewAllNavGraph(
    action: (PlaceUi) -> Unit,
    onBack: () -> Unit,
) {
    composable(
        route = NavRoute.ViewAll.route,
        arguments = listOf(navArgument("places") { type = NavType.StringType })
    ) { backStackEntry ->
        val places = backStackEntry.arguments?.getString("places")
        val json = URLDecoder.decode(places, StandardCharsets.UTF_8.toString())
        val placesUi = json?.let {
            val typeTokenList = object : TypeToken<List<PlaceUi>>() {}.type
            Gson().fromJson<List<PlaceUi>>(it, typeTokenList)
        }.orEmpty()
        ViewAllScreen(
            places = placesUi,
            onCardClick = action,
            onBack = onBack
        )
    }
}