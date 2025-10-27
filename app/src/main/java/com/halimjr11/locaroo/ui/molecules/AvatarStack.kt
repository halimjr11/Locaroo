package com.halimjr11.locaroo.ui.molecules

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun AvatarStack(
    painters: List<Painter?>,
    size: Dp = 28.dp,
    overlap: Dp = 10.dp
) {
    Row(horizontalArrangement = Arrangement.spacedBy((-overlap))) {
        painters.forEachIndexed { index, painter ->
            val p = painter ?: ColorPainter(androidx.compose.ui.graphics.Color.LightGray)
            Image(
                painter = p,
                contentDescription = null,
                modifier = Modifier
                    .size(size)
                    .clip(CircleShape)
                    .offset(x = (index * -overlap.value).dp)
            )
        }
    }
}
