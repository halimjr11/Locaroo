package com.halimjr11.locaroo.ui.organisms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.unit.dp
import com.halimjr11.locaroo.model.Destination
import com.halimjr11.locaroo.ui.molecules.DestinationCard

@Composable
fun DestinationCarousel(
    modifier: Modifier = Modifier,
    items: List<Destination>,
    onCardClick: (Destination) -> Unit = {},
    onBookmarkClick: (Destination) -> Unit = {}
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items, key = { it.id }) { dest ->
            val painter =
                if (dest.imageUrl == null) {
                    ColorPainter(MaterialTheme.colorScheme.secondaryContainer)
                } else {
                    ColorPainter(MaterialTheme.colorScheme.secondaryContainer)
                }
            DestinationCard(
                imagePainter = painter,
                name = dest.name,
                location = dest.location,
                rating = dest.rating,
                bookmarkPainter = null,
                onCardClick = { onCardClick(dest) },
                onBookmarkClick = { onBookmarkClick(dest) }
            )
        }
    }
}
