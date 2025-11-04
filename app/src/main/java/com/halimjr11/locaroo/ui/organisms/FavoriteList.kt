package com.halimjr11.locaroo.ui.organisms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.halimjr11.locaroo.ui.model.PlaceUi
import com.halimjr11.locaroo.ui.molecules.FavoriteCard

@Composable
fun FavoriteCarousel(
    modifier: Modifier = Modifier,
    items: List<PlaceUi>,
    onCardClick: (PlaceUi) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = 8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items, key = { it.id }) { place ->
            FavoriteCard(
                imageUrl = place.imageUrl,
                name = place.name,
                location = place.location,
                rating = place.rating,
                onCardClick = { onCardClick(place) }
            )
        }
    }
}
