package com.halimjr11.locaroo.view.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.halimjr11.locaroo.R
import com.halimjr11.locaroo.ui.model.PlaceUi
import com.halimjr11.locaroo.ui.molecules.ErrorState
import com.halimjr11.locaroo.ui.molecules.SectionHeader
import com.halimjr11.locaroo.ui.organisms.CityRecommendations
import com.halimjr11.locaroo.ui.organisms.DestinationCarousel
import com.halimjr11.locaroo.ui.organisms.HomeHeader
import com.halimjr11.locaroo.ui.state.UiState
import com.halimjr11.locaroo.ui.theme.LocarooTheme
import com.halimjr11.locaroo.utils.SampleData
import com.halimjr11.locaroo.view.viewmodels.home.HomeViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onCardClick: (PlaceUi) -> Unit,
    onAddPlace: () -> Unit,
) {
    val viewModel = hiltViewModel<HomeViewModel>()
    val state by viewModel.places.collectAsStateWithLifecycle()

    Box(modifier = modifier.fillMaxSize()) {
        when (state) {
            is UiState.Success -> {
                val (places, title) = (state as UiState.Success<Pair<List<PlaceUi>, String>>).data
                HomeScreenContent(modifier, title, onCardClick, places)
            }

            is UiState.Loading -> {
                CircularProgressIndicator(
                    modifier = modifier.align(Alignment.Center)
                )
            }

            is UiState.Error -> {
                ErrorState(
                    modifier = modifier.align(Alignment.Center),
                    title = stringResource(R.string.error_title),
                    message = (state as UiState.Error).message,
                    onRetry = { viewModel.loadPlaces() }
                )
            }
        }

        FloatingActionButton(
            onClick = onAddPlace,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "Add place")
        }
    }
}

@Composable
private fun HomeScreenContent(
    modifier: Modifier = Modifier,
    title: String,
    onCardClick: (PlaceUi) -> Unit,
    places: List<PlaceUi>
) {
    val cities = places.map { it.location }.distinct()
    val (selectedCity, _) = remember { mutableStateOf(cities.firstOrNull()) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        HomeHeader(userName = title, onNotifClick = {})
        CityRecommendations(
            places = places.filter { it.location == selectedCity },
            selectedCity = selectedCity,
        )
        Spacer(Modifier.height(16.dp))
        SectionHeader(title = stringResource(R.string.best_destination_title), onActionClick = {})
        DestinationCarousel(
            items = places,
            onCardClick = onCardClick
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    LocarooTheme(darkTheme = false, dynamicColor = false) {
        HomeScreenContent(places = SampleData.destinations, title = "Leonardo", onCardClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreviewDark() {
    LocarooTheme(darkTheme = true, dynamicColor = false) {
        HomeScreenContent(places = SampleData.destinations, title = "Leonardo", onCardClick = {})
    }
}



