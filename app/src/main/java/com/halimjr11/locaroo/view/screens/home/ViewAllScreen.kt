package com.halimjr11.locaroo.view.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.halimjr11.locaroo.R
import com.halimjr11.locaroo.ui.model.PlaceUi
import com.halimjr11.locaroo.ui.molecules.DestinationCard
import kotlinx.coroutines.delay

@Composable
fun ViewAllScreen(
    modifier: Modifier = Modifier,
    places: List<PlaceUi> = emptyList(),
    onCardClick: (PlaceUi) -> Unit,
    onBack: () -> Unit,
) {
    val loading = remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(1000)
        loading.value = false
    }

    Box(modifier.fillMaxSize()) {
        if (loading.value) {
            CircularProgressIndicator(modifier = modifier.align(Alignment.Center))
        } else {
            ViewAllContent(places = places, onCardClick = onCardClick, onBack = onBack)
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ViewAllContent(
    modifier: Modifier = Modifier,
    places: List<PlaceUi>,
    onCardClick: (PlaceUi) -> Unit,
    onBack: () -> Unit,
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = modifier.fillMaxSize()) {
            CenterAlignedTopAppBar(
                modifier = modifier.background(MaterialTheme.colorScheme.primary),
                windowInsets = WindowInsets(0),
                title = {
                    Text(
                        text = stringResource(R.string.view_all_destination_title),
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.background(MaterialTheme.colorScheme.surface),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(places) { place ->
                    DestinationCard(
                        imageUrl = place.imageUrl,
                        name = place.name,
                        location = place.location,
                        rating = place.rating,
                        onCardClick = { onCardClick(place) }
                    )
                }
            }
        }
    }
}
