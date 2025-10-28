package com.halimjr11.locaroo.ui.screens.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.halimjr11.locaroo.ui.molecules.IconTextRow
import com.halimjr11.locaroo.ui.molecules.PriceTag
import com.halimjr11.locaroo.ui.molecules.PrimaryButton
import com.halimjr11.locaroo.ui.organisms.AboutSection
import com.halimjr11.locaroo.ui.organisms.TopImageHeader
import com.halimjr11.locaroo.ui.theme.LocarooTheme

@Composable
fun DetailScreen(
    title: String,
    location: String,
    rating: Double,
    priceText: String,
    headerImage: String,
    onBack: () -> Unit = {},
    onBookmark: () -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopImageHeader(
            image = headerImage,
            onBack = onBack,
            onBookmark = onBookmark
        )
        Surface(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Title + host avatar
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(Modifier.weight(1f)) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = location,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }
                Spacer(Modifier.height(12.dp))
                // Meta row: location + rating + price per person
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    IconTextRow(icon = null, text = location)
                    Text(text = "•", color = MaterialTheme.colorScheme.outline)
                    IconTextRow(icon = null, text = String.format("%.1f", rating))
                    Spacer(Modifier.weight(1f))
                    PriceTag(priceText = priceText)
                }
                Spacer(Modifier.height(16.dp))
                AboutSection(
                    description = "You will get a complete travel package on the beaches. Packages in the form of airline tickets, recommended hotel rooms, transportation. Have you ever been on holiday to the Greek, etc..."
                )
                Spacer(Modifier.height(20.dp))
                PrimaryButton(text = "Book Now", modifier = Modifier.fillMaxWidth()) { }
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailScreenPreviewLight() {
    LocarooTheme(darkTheme = false, dynamicColor = false) {
        DetailScreen(
            title = "Niladri Reservoir",
            location = "Tekergat, Sunamganj",
            rating = 4.7,
            priceText = "$59/Person",
            headerImage = ColorPainter(MaterialTheme.colorScheme.secondaryContainer)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailScreenPreviewDark() {
    LocarooTheme(darkTheme = true, dynamicColor = false) {
        DetailScreen(
            title = "Niladri Reservoir",
            location = "Tekergat, Sunamganj",
            rating = 4.7,
            priceText = "$59/Person",
            headerImage = ColorPainter(MaterialTheme.colorScheme.secondaryContainer)
        )
    }
}
