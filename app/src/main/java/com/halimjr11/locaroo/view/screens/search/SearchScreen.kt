package com.halimjr11.locaroo.view.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.halimjr11.locaroo.ui.organisms.DestinationCarousel
import com.halimjr11.locaroo.ui.state.UiState
import com.halimjr11.locaroo.ui.theme.LocarooTheme
import com.halimjr11.locaroo.view.viewmodels.search.SearchViewModel

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    onPlaceClick: (PlaceUi) -> Unit = {}
) {
    val viewModel = hiltViewModel<SearchViewModel>()
    val query by viewModel.query.collectAsStateWithLifecycle()
    val state by viewModel.searchResults.collectAsStateWithLifecycle()

    when (state) {
        is UiState.Success -> {
            val data = (state as UiState.Success<List<PlaceUi>>).data
            if (data.isNotEmpty()) {
                SearchScreenContent(
                    modifier = modifier,
                    items = data,
                    query = query,
                    onValueChange = { viewModel.updateQuery(it) },
                    onBack = onBack,
                    onCardClick = onPlaceClick,
                )
            } else {
                ErrorState(
                    title = stringResource(R.string.empty_title),
                    message = stringResource(R.string.empty_messages, query)
                )
            }
        }

        is UiState.Loading -> {
            CircularProgressIndicator()
        }

        is UiState.Error -> {
            ErrorState(
                title = stringResource(R.string.error_message),
                message = (state as UiState.Error).message
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchScreenContent(
    modifier: Modifier = Modifier,
    items: List<PlaceUi>,
    query: String,
    onValueChange: (String) -> Unit,
    onBack: () -> Unit,
    onCardClick: (PlaceUi) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        CenterAlignedTopAppBar(
            title = { Text(text = "Search", style = MaterialTheme.typography.titleMedium) },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        )

        Spacer(Modifier.height(12.dp))

        // Search field
        OutlinedTextField(
            value = query,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            placeholder = { Text("Search Places") },
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
            },
            singleLine = true
        )

        Spacer(Modifier.height(16.dp))

        SectionHeader(title = "Search Places")

        DestinationCarousel(
            items = items,
            onCardClick = onCardClick,
            onBookmarkClick = { /* no-op for now */ }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchScreenPreviewLight() {
    LocarooTheme(darkTheme = false, dynamicColor = false) {
        SearchScreen()
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchScreenPreviewDark() {
    LocarooTheme(darkTheme = true, dynamicColor = false) {
        SearchScreen()
    }
}
