package com.halimjr11.locaroo.ui.atoms

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun AppChip(
    label: String,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    leadingIcon: Painter? = null,
    onClick: () -> Unit = {}
) {
    val selectedContainer = MaterialTheme.colorScheme.secondaryContainer
    val selectedLabel = MaterialTheme.colorScheme.onSecondaryContainer
    val unselectedContainer = Color.Transparent
    val unselectedLabel = MaterialTheme.colorScheme.onSurface

    FilterChip(
        selected = selected,
        onClick = onClick,
        label = { Text(label) },
        leadingIcon = if (leadingIcon != null) {
            { Icon(painter = leadingIcon, contentDescription = null) }
        } else null,
        colors = FilterChipDefaults.filterChipColors(
            containerColor = unselectedContainer,
            labelColor = unselectedLabel,
            selectedContainerColor = selectedContainer,
            selectedLabelColor = selectedLabel,
        ),
        modifier = modifier
    )
}
