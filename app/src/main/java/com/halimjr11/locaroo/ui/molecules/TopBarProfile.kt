package com.halimjr11.locaroo.ui.molecules

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.halimjr11.locaroo.ui.atoms.AppAvatar
import com.halimjr11.locaroo.ui.atoms.AppIconButton
import com.halimjr11.locaroo.ui.atoms.IconButtonVariant
import com.halimjr11.locaroo.ui.atoms.NotificationBadge

@Composable
fun TopBarProfile(
    name: String,
    avatarPainter: Painter? = null,
    notifIcon: Painter? = null,
    notifCount: Int = 0,
    onNotifClick: () -> Unit = {}
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

        if (notifIcon != null) {
            androidx.compose.foundation.layout.Box {
                AppIconButton(
                    painter = notifIcon,
                    contentDescription = "Notifications",
                    size = 40.dp,
                    variant = IconButtonVariant.Tinted,
                    onClick = onNotifClick
                )
                NotificationBadge(count = notifCount, modifier = Modifier.align(Alignment.TopEnd))
            }
        }
    }
}
