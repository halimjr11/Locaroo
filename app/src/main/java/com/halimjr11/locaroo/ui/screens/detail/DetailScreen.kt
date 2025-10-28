package com.halimjr11.locaroo.ui.screens.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.halimjr11.locaroo.ui.molecules.IconTextRow
import com.halimjr11.locaroo.ui.molecules.PrimaryButton
import com.halimjr11.locaroo.ui.organisms.AboutSection
import com.halimjr11.locaroo.ui.organisms.TopImageHeader
import com.halimjr11.locaroo.ui.theme.LocarooTheme

@Composable
fun DetailScreen(
    title: String,
    location: String,
    rating: Float,
    description: String,
    headerImage: String,
    onBack: () -> Unit = {},
    onBookmark: () -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize()) {
        TopImageHeader(
            image = headerImage,
            onBack = onBack,
            onBookmark = onBookmark
        )
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 200.dp),
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
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
                Spacer(Modifier.height(12.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    IconTextRow(
                        icon = rememberVectorPainter(Icons.Filled.LocationOn),
                        text = location
                    )
                    Text(text = "•", color = MaterialTheme.colorScheme.onSurface)
                    IconTextRow(
                        icon = rememberVectorPainter(Icons.Filled.Star),
                        iconTint = MaterialTheme.colorScheme.primary,
                        text = rating.toString()
                    )
                    Spacer(Modifier.weight(1f))
                }
                Spacer(Modifier.height(16.dp))
                AboutSection(
                    description = description
                )
                Spacer(Modifier.height(20.dp))
                PrimaryButton(text = "Plan Now", modifier = Modifier.fillMaxWidth()) { }
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
            rating = 4.7f,
            description = "You will get a complete travel package on the beaches. Packages in the form of airline tickets, recommended hotel rooms, transportation. Have you ever been on holiday to the Greek, etc...",
            headerImage = ""
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
            rating = 4.7f,
            description = "You will get a complete travel package on the beaches. Packages in the form of airline tickets, recommended hotel rooms, transportation. Have you ever been on holiday to the Greek, etc...",
            headerImage = ""
        )
    }
}
