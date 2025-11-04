package com.halimjr11.locaroo.ui.organisms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.halimjr11.locaroo.R
import com.halimjr11.locaroo.ui.model.PlaceUi
import com.halimjr11.locaroo.ui.molecules.DestinationCard
import com.halimjr11.locaroo.ui.molecules.SectionHeader

@Composable
fun CityRecommendations(
    modifier: Modifier = Modifier,
    title: String = stringResource(R.string.recommended_title),
    places: List<PlaceUi>,
    selectedCity: String? = null,
    onPlaceClick: (PlaceUi) -> Unit = {},
) {
    SectionHeader(title = title, onActionClick = {})
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val filtered = places.filter {
            selectedCity == null || it.location.equals(
                selectedCity,
                ignoreCase = true
            )
        }
        items(filtered, key = { it.id }) { dest ->
            DestinationCard(
                imageUrl = dest.imageUrl,
                name = dest.name,
                location = dest.location,
                rating = dest.rating,
                onCardClick = { onPlaceClick(dest) },
            )
        }
    }
}
