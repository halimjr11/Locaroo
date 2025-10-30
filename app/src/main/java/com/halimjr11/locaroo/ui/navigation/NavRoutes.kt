package com.halimjr11.locaroo.ui.navigation

sealed class NavRoute(val route: String) {
    data object Home : NavRoute("home")
    data object Search : NavRoute("search")
    data object Detail : NavRoute("detail/{placeId}")
    data object Journey : NavRoute("journey")
}
