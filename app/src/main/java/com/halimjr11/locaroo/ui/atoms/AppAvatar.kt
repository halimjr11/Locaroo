package com.halimjr11.locaroo.ui.atoms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image

@Composable
fun AppAvatar(
    modifier: Modifier = Modifier,
    size: Dp = 40.dp,
    background: Color = MaterialTheme.colorScheme.secondaryContainer,
    contentColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
    initials: String? = null,
    imagePainter: Painter? = null,
    contentDescription: String? = null
) {
    val base = modifier
        .size(size)
        .clip(CircleShape)

    if (imagePainter != null) {
        Image(
            painter = imagePainter,
            contentDescription = contentDescription,
            modifier = base,
            contentScale = ContentScale.Crop
        )
    } else {
        Box(
            modifier = base.background(background),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = (initials ?: "").take(2).uppercase(),
                color = contentColor,
                textAlign = TextAlign.Center,
                fontSize = (size.value / 2.8).sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
