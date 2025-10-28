package com.halimjr11.locaroo.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.halimjr11.locaroo.ui.molecules.SectionHeader
import com.halimjr11.locaroo.ui.organisms.CityRecommendations
import com.halimjr11.locaroo.ui.organisms.DestinationCarousel
import com.halimjr11.locaroo.ui.organisms.HomeHeader
import com.halimjr11.locaroo.ui.theme.LocarooTheme
import com.halimjr11.locaroo.utils.SampleData

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val destinations = SampleData.destinations
    val cities = destinations.map { it.location }.distinct()
    val (selectedCity, _) = remember { mutableStateOf(cities.firstOrNull()) }

    Column(
        modifier = modifier.background(color = MaterialTheme.colorScheme.surface)
    ) {
        HomeHeader(userName = "Leonardo", onNotifClick = {})
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
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    LocarooTheme(darkTheme = false, dynamicColor = false) {
        HomeScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreviewDark() {
    LocarooTheme(darkTheme = true, dynamicColor = false) {
        HomeScreen()
    }
}


