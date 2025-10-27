package com.halimjr11.locaroo.ui.screens.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.halimjr11.locaroo.ui.molecules.BottomNavItem
import com.halimjr11.locaroo.ui.molecules.SectionHeader
import com.halimjr11.locaroo.ui.organisms.CityRecommendations
import com.halimjr11.locaroo.ui.organisms.DestinationCarousel
import com.halimjr11.locaroo.ui.organisms.HomeHeader
import com.halimjr11.locaroo.ui.template.HomeTemplate
import com.halimjr11.locaroo.ui.theme.LocarooTheme
import com.halimjr11.locaroo.utils.SampleData

@Composable
fun HomeScreen() {
    val bottomItems = listOf(
        BottomNavItem(icon = null, label = "Home"),
        BottomNavItem(icon = null, label = "Calendar"),
        BottomNavItem(icon = null, label = "Search"),
        BottomNavItem(icon = null, label = "Messages"),
        BottomNavItem(icon = null, label = "Profile"),
    )

    val destinations = SampleData.destinations
    val cities = destinations.map { it.location }.distinct()
    val (selectedCity, setSelectedCity) = remember { mutableStateOf(cities.firstOrNull()) }

    HomeTemplate(
        bottomItems = bottomItems,
        selectedIndex = 0,
        onBottomItemSelected = {},
        fabIcon = null,
        onFabClick = {},
        header = {
            HomeHeader(userName = "Leonardo", onNotifClick = {})
        },
        content = {
            CityRecommendations(
                places = destinations.filter { it.location == selectedCity },
                selectedCity = selectedCity,
                onPlaceClick = {},
                onBookmarkClick = {}
            )
            Spacer(Modifier.height(16.dp))
            SectionHeader(title = "Best destination", onActionClick = {})
            DestinationCarousel(
                items = destinations,
                onCardClick = {},
                onBookmarkClick = {}
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreviewLight() {
    LocarooTheme(darkTheme = false, dynamicColor = false) {
        HomeScreen()
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreviewDark() {
    LocarooTheme(darkTheme = true, dynamicColor = false) {
        HomeScreen()
    }
}
