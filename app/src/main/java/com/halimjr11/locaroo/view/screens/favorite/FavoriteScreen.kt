package com.halimjr11.locaroo.view.screens.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.halimjr11.locaroo.ui.organisms.FavoriteCarousel
import com.halimjr11.locaroo.ui.state.UiState
import com.halimjr11.locaroo.ui.theme.LocarooTheme
import com.halimjr11.locaroo.utils.SampleData
import com.halimjr11.locaroo.view.viewmodels.favorite.FavoriteViewModel

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    onCardClick: (PlaceUi) -> Unit = {}
) {
    val viewModel = hiltViewModel<FavoriteViewModel>()
    val state by viewModel.favorites.collectAsStateWithLifecycle()

    Box(modifier = modifier.fillMaxSize()) {
        when (state) {
            is UiState.Success -> {
                val items = (state as UiState.Success<List<PlaceUi>>).data
                if (items.isNotEmpty()) {
                    FavoriteScreenContent(
                        modifier = modifier,
                        items = items,
                        onCardClick = onCardClick
                    )
                } else {
                    ErrorState(title = "Empty", message = "Belum ada tempat favorit")
                }
            }

            is UiState.Loading -> CircularProgressIndicator(modifier = modifier.align(Alignment.Center))
            is UiState.Error -> ErrorState(
                modifier = modifier.align(Alignment.Center),
                title = stringResource(R.string.empty_title),
                message = (state as UiState.Error).message
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreenContent(
    modifier: Modifier = Modifier,
    items: List<PlaceUi>,
    onCardClick: (PlaceUi) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        CenterAlignedTopAppBar(title = {
            Text(
                text = "Favorites",
                style = MaterialTheme.typography.titleMedium
            )
        })
        Spacer(Modifier.height(16.dp))
        FavoriteCarousel(items = items, onCardClick = onCardClick)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFavoriteLight() {
    LocarooTheme(darkTheme = false, dynamicColor = false) {
        FavoriteScreenContent(items = SampleData.destinations, onCardClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFavoriteDark() {
    LocarooTheme(darkTheme = true, dynamicColor = false) {
        FavoriteScreenContent(items = SampleData.destinations, onCardClick = {})
    }
}

