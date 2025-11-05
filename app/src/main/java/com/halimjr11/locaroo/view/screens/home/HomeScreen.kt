package com.halimjr11.locaroo.view.screens.home

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.gson.Gson
import com.halimjr11.locaroo.R
import com.halimjr11.locaroo.ui.model.PlaceUi
import com.halimjr11.locaroo.ui.molecules.ErrorState
import com.halimjr11.locaroo.ui.molecules.SectionHeader
import com.halimjr11.locaroo.ui.organisms.CityRecommendations
import com.halimjr11.locaroo.ui.organisms.DestinationCarousel
import com.halimjr11.locaroo.ui.organisms.HomeHeader
import com.halimjr11.locaroo.ui.state.UiState
import com.halimjr11.locaroo.ui.theme.LocarooTheme
import com.halimjr11.locaroo.utils.Constant.BEST_DESTINATION
import com.halimjr11.locaroo.utils.Constant.HOME_LIMIT
import com.halimjr11.locaroo.utils.SampleData
import com.halimjr11.locaroo.utils.location.ensureLocationPermission
import com.halimjr11.locaroo.utils.location.fetchLastKnownLocation
import com.halimjr11.locaroo.utils.location.hasLocationPermission
import com.halimjr11.locaroo.view.viewmodels.home.HomeViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onCardClick: (PlaceUi) -> Unit,
    onAboutClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onAddPlace: () -> Unit,
    onViewAllClick: (String) -> Unit,
) {
    val viewModel = hiltViewModel<HomeViewModel>()
    val state by viewModel.places.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var locationName by rememberSaveable { mutableStateOf<String?>(null) }

    val requestLocationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { results ->
            val granted = results[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                    results[Manifest.permission.ACCESS_COARSE_LOCATION] == true
            if (granted) {
                fetchLastKnownLocation(context) { _, address, city ->
                    if (!address.isNullOrBlank()) locationName = city
                    viewModel.loadPlaces(locationName.orEmpty())
                }
            }
        }
    )

    LaunchedEffect(Unit) {
        if (hasLocationPermission(context)) {
            fetchLastKnownLocation(context) { _, address, city ->
                if (!address.isNullOrBlank()) locationName = city
                viewModel.loadPlaces(locationName.orEmpty())
            }
        } else {
            ensureLocationPermission(context) { perms ->
                requestLocationPermissionLauncher.launch(perms)
            }
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        when (state) {
            is UiState.Success -> {
                val (city, places, title) = (state as UiState.Success<Triple<List<PlaceUi>, List<PlaceUi>, String>>).data
                HomeScreenContent(
                    modifier = modifier,
                    title = title,
                    places = places.take(HOME_LIMIT),
                    recommended = city.take(HOME_LIMIT),
                    onCardClick = onCardClick,
                    onAboutClick = onAboutClick,
                    onViewAllClick = { title ->
                        println("Jalanan ==> $title")
                        val list = if (title == BEST_DESTINATION) {
                            places
                        } else {
                            city
                        }
                        val places = Gson().toJson(list)
                        val encoded = URLEncoder.encode(places, StandardCharsets.UTF_8.toString())
                        onViewAllClick(encoded)
                    },
                    onFavoriteClick = onFavoriteClick
                )
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
                    onRetry = { viewModel.loadPlaces(locationName.orEmpty()) }
                )
            }

            else -> Unit
        }

        FloatingActionButton(
            onClick = onAddPlace,
            modifier = modifier
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
    places: List<PlaceUi>,
    recommended: List<PlaceUi>,
    onCardClick: (PlaceUi) -> Unit,
    onAboutClick: () -> Unit,
    onViewAllClick: (String) -> Unit,
    onFavoriteClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        HomeHeader(userName = title, onAboutClick = onAboutClick, onFavoriteClick = onFavoriteClick)
        CityRecommendations(
            places = recommended,
            onPlaceClick = onCardClick,
            onViewAllClick = onViewAllClick
        )
        Spacer(Modifier.height(16.dp))
        SectionHeader(
            title = stringResource(R.string.best_destination_title),
            onActionClick = onViewAllClick
        )
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
        HomeScreenContent(
            places = SampleData.destinations,
            title = "Leonardo",
            onAboutClick = {},
            onViewAllClick = {},
            onCardClick = {},
            recommended = SampleData.destinations,
            onFavoriteClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreviewDark() {
    LocarooTheme(darkTheme = true, dynamicColor = false) {
        HomeScreenContent(
            places = SampleData.destinations,
            title = "Leonardo",
            onAboutClick = {},
            onViewAllClick = {},
            onCardClick = {},
            recommended = SampleData.destinations,
            onFavoriteClick = {}
        )
    }
}
