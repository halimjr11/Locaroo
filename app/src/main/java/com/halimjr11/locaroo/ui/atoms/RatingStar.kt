package com.halimjr11.locaroo.ui.atoms

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Lightweight rating stars without icon dependency using unicode stars.
 * Shows up to [max] stars, supports half star by rounding to nearest 0.5.
 */
@Composable
fun RatingStar(
    rating: Double,
    modifier: Modifier = Modifier,
    max: Int = 5,
    size: Dp = 14.dp,
    filledColor: Color = MaterialTheme.colorScheme.tertiary,
    emptyColor: Color = MaterialTheme.colorScheme.outline,
) {
    val rounded = (rating * 2).toInt() / 2.0
    val full = kotlin.math.floor(rounded).toInt()
    val half = if (rounded - full >= 0.5) 1 else 0
    val empty = (max - full - half).coerceAtLeast(0)

    Row(modifier = modifier) {
        repeat(full) {
            Text("★", color = filledColor, fontSize = size.value.sp, fontWeight = FontWeight.SemiBold)
        }
        repeat(half) {
            // Half star approximated using different glyph; keep same color for visibility
            Text("⯪", color = filledColor, fontSize = size.value.sp, fontWeight = FontWeight.SemiBold)
        }
        repeat(empty) {
            Text("☆", color = emptyColor, fontSize = size.value.sp)
        }
    }
}
