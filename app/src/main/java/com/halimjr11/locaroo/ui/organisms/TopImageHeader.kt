package com.halimjr11.locaroo.ui.organisms

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.halimjr11.locaroo.ui.atoms.AppIconButton
import com.halimjr11.locaroo.ui.atoms.IconButtonVariant

@Composable
fun TopImageHeader(
    image: Painter?,
    onBack: () -> Unit,
    onBookmark: () -> Unit,
    backIcon: Painter? = null,
    bookmarkIcon: Painter? = null
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
    ) {
        val painter = image ?: ColorPainter(MaterialTheme.colorScheme.secondaryContainer)
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.0f))
        )
        Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            if (backIcon != null) {
                AppIconButton(
                    painter = backIcon,
                    contentDescription = "Back",
                    variant = IconButtonVariant.Tinted,
                    onClick = onBack
                )
            }
            if (bookmarkIcon != null) {
                AppIconButton(
                    painter = bookmarkIcon,
                    contentDescription = "Bookmark",
                    modifier = Modifier.align(Alignment.TopEnd),
                    variant = IconButtonVariant.Tinted,
                    onClick = onBookmark
                )
            }
        }
    }
}
