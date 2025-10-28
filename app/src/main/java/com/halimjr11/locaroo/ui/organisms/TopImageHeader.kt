package com.halimjr11.locaroo.ui.organisms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.halimjr11.locaroo.R
import com.halimjr11.locaroo.ui.atoms.AppIconButton
import com.halimjr11.locaroo.ui.atoms.IconButtonVariant

@Composable
fun TopImageHeader(
    image: String,
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
        AsyncImage(
            model = image,
            contentDescription = "",
            placeholder = painterResource(R.drawable.ic_launcher_background),
            error = painterResource(R.drawable.ic_launcher_background),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.0f))
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
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
