package com.halimjr11.locaroo.ui.organisms

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.halimjr11.locaroo.ui.molecules.BottomNavBar
import com.halimjr11.locaroo.ui.molecules.BottomNavItem

@Composable
fun HomeBottomBar(
    items: List<BottomNavItem>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    Surface(shadowElevation = 8.dp) {
        BottomNavBar(items = items, selectedIndex = selectedIndex, onItemSelected = onItemSelected)
    }
}
