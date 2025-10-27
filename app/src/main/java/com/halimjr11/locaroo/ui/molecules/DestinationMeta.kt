package com.halimjr11.locaroo.ui.molecules

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun DestinationMeta(
    name: String,
    location: String,
    rating: Double,
    locationIcon: ImageVector? = null,
    starIcon: ImageVector? = null,
    modifier: Modifier = Modifier
) {
    Text(text = name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        if (locationIcon != null) Icon(locationIcon, contentDescription = null, tint = MaterialTheme.colorScheme.outline)
        Text(text = location, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.outline)
        Text(text = "•", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.outline)
        if (starIcon != null) Icon(starIcon, contentDescription = null, tint = MaterialTheme.colorScheme.tertiary)
        Text(text = String.format("%.1f", rating), style = MaterialTheme.typography.bodySmall)
    }
}
