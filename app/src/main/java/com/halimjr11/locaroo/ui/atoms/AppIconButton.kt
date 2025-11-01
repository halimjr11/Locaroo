package com.halimjr11.locaroo.ui.atoms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class IconButtonVariant { Plain, Filled, Tinted }

@Composable
fun AppIconButton(
    painter: Painter,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    size: Dp = 40.dp,
    tint: Color = MaterialTheme.colorScheme.onSurface,
    containerColor: Color? = null,
    variant: IconButtonVariant = IconButtonVariant.Plain,
    onClick: () -> Unit
) {
    val shape = CircleShape
    val base = modifier.size(size)

    when (variant) {
        IconButtonVariant.Plain -> {
            Box(
                modifier = base.clip(shape),
                contentAlignment = Alignment.Center
            ) {
                IconButton(onClick = onClick, modifier = base) {
                    Icon(painter = painter, contentDescription = contentDescription, tint = tint)
                }
            }
        }

        IconButtonVariant.Filled -> {
            Box(
                modifier = base
                    .clip(shape)
                    .background(containerColor ?: MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                IconButton(onClick = onClick, modifier = Modifier.size(size)) {
                    Icon(
                        painter = painter,
                        contentDescription = contentDescription,
                        tint = tint
                    )
                }
            }
        }

        IconButtonVariant.Tinted -> {
            Box(
                modifier = base
                    .clip(shape)
                    .background(containerColor ?: MaterialTheme.colorScheme.secondaryContainer),
                contentAlignment = Alignment.Center
            ) {
                IconButton(onClick = onClick, modifier = Modifier.size(size)) {
                    Icon(
                        painter = painter,
                        contentDescription = contentDescription,
                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
        }
    }
}
