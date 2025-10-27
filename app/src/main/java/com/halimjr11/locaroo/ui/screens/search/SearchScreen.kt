package com.halimjr11.locaroo.ui.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.halimjr11.locaroo.model.Destination
import com.halimjr11.locaroo.ui.molecules.SectionHeader
import com.halimjr11.locaroo.ui.organisms.DestinationCarousel
import com.halimjr11.locaroo.ui.theme.LocarooTheme
import com.halimjr11.locaroo.utils.SampleData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    initialQuery: String = "",
    items: List<Destination> = SampleData.destinations,
    onBack: () -> Unit = {},
    onPlaceClick: (Destination) -> Unit = {}
) {
    var query by remember { mutableStateOf(TextFieldValue(initialQuery)) }

    val filtered = remember(query.text, items) {
        val q = query.text.trim()
        if (q.isEmpty()) items
        else items.filter { dest ->
            dest.name.contains(q, ignoreCase = true) ||
                    dest.location.contains(q, ignoreCase = true)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        // Material app bar with back icon and centered title
        CenterAlignedTopAppBar(
            title = { Text(text = "Search", style = MaterialTheme.typography.titleMedium) },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        )

        Spacer(Modifier.height(12.dp))

        // Search field
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
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

        if (filtered.isEmpty()) {
            Spacer(Modifier.height(24.dp))
            Text(
                text = "No results",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        } else {
            DestinationCarousel(
                items = filtered,
                onCardClick = onPlaceClick,
                onBookmarkClick = { /* no-op for now */ }
            )
        }
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
