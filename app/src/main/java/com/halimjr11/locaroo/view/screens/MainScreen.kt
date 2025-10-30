package com.halimjr11.locaroo.view.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.halimjr11.locaroo.ui.molecules.BottomNavBar
import com.halimjr11.locaroo.ui.molecules.BottomNavItem
import com.halimjr11.locaroo.ui.navigation.AppNavGraph
import com.halimjr11.locaroo.ui.navigation.NavRoute

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val bottomItems = listOf(
        BottomNavItem(icon = rememberVectorPainter(Icons.Filled.Home), label = "Home"),
        BottomNavItem(icon = rememberVectorPainter(Icons.Filled.Search), label = "Search"),
        BottomNavItem(icon = rememberVectorPainter(Icons.Filled.DateRange), label = "Journey"),
    )
    val routes = listOf(
        NavRoute.Home.route,
        NavRoute.Search.route,
        NavRoute.Journey.route,
    )
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val selectedIndex = routes.indexOf(currentRoute).let { if (it >= 0) it else 0 }

    Scaffold(
        bottomBar = {
            BottomNavBar(
                items = bottomItems,
                selectedIndex = selectedIndex,
                onItemSelected = { index ->
                    val target = routes.getOrNull(index) ?: return@BottomNavBar
                    if (currentRoute != target) {
                        navController.navigate(target) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        AppNavGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

