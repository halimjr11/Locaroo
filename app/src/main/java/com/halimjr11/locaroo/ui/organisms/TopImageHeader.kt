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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.halimjr11.locaroo.R
import com.halimjr11.locaroo.ui.atoms.AppIconButton
import com.halimjr11.locaroo.ui.atoms.IconButtonVariant

@Composable
fun TopImageHeader(
    image: String,
    isFavorite: Boolean,
    onBack: () -> Unit,
    onFavorite: () -> Unit,
    backIcon: Painter? = null,
    favoriteIcon: Painter? = null
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
                    contentDescription = stringResource(R.string.back),
                    variant = IconButtonVariant.Tinted,
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    onClick = onBack
                )
            }
            if (favoriteIcon != null) {
                AppIconButton(
                    painter = favoriteIcon,
                    contentDescription = stringResource(R.string.favorite),
                    modifier = Modifier.align(Alignment.TopEnd),
                    variant = IconButtonVariant.Filled,
                    tint = if (isFavorite) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary,
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    onClick = onFavorite
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopImageHeaderPreview() {
    TopImageHeader(
        image = "https://images.unsplash.com/photo-1511485977113-f34c92461ad9?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80",
        isFavorite = true,
        onBack = {},
        onFavorite = {},
        backIcon = painterResource(R.drawable.ic_back),
        favoriteIcon = painterResource(R.drawable.ic_favorite)
    )
}

