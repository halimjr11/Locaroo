package com.halimjr11.locaroo.ui.molecules

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.halimjr11.locaroo.R
import com.halimjr11.locaroo.ui.atoms.AppAvatar
import com.halimjr11.locaroo.ui.atoms.AppIconButton
import com.halimjr11.locaroo.ui.atoms.IconButtonVariant

@Composable
fun TopBarProfile(
    name: String,
    avatarPainter: Painter? = null,
    aboutIcon: Painter? = null,
    favoriteIcon: Painter? = null,
    onAboutClick: () -> Unit = {},
    onFavoriteClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AppAvatar(
                size = 36.dp,
                imagePainter = avatarPainter,
                initials = name.firstOrNull()?.toString()
            )
            Text(
                text = name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(8.dp, 0.dp, 0.dp, 0.dp)
            )

        }

        Row(horizontalArrangement = Arrangement.End) {
            if (aboutIcon != null) {
                AppIconButton(
                    painter = aboutIcon,
                    contentDescription = stringResource(R.string.about_icon_desc),
                    size = 40.dp,
                    variant = IconButtonVariant.Tinted,
                    onClick = onAboutClick
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            if (favoriteIcon != null) {
                AppIconButton(
                    painter = favoriteIcon,
                    contentDescription = stringResource(R.string.favorite),
                    size = 40.dp,
                    variant = IconButtonVariant.Tinted,
                    onClick = onFavoriteClick
                )
            }
        }
    }
}
