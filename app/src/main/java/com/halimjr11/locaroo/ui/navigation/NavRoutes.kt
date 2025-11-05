package com.halimjr11.locaroo.ui.navigation

sealed class NavRoute(val route: String) {
    data object Splash : NavRoute("splash")
    data object About : NavRoute("about")
    data object Home : NavRoute("home")
    data object Login : NavRoute("login")
    data object Main : NavRoute("main")
    data object Register : NavRoute("register")
    data object Search : NavRoute("search")
    data object Detail : NavRoute("detail/{placeId}")
    data object Journey : NavRoute("journey")
    data object Favorite : NavRoute("favorite")
    data object Create : NavRoute("create")
    data object ViewAll : NavRoute("view_all/{places}")
}
