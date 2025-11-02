package com.halimjr11.locaroo.ui.organisms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.halimjr11.locaroo.R
import com.halimjr11.locaroo.ui.atoms.AppAvatar
import com.halimjr11.locaroo.ui.atoms.BodyM
import com.halimjr11.locaroo.ui.atoms.TitleXL
import com.halimjr11.locaroo.ui.theme.LocarooTheme

@Composable
fun AboutScreen(
    modifier: Modifier = Modifier,
    name: String = "John Doe",
    email: String = "john.doe@example.com",
    imageRes: Int = R.drawable.ic_launcher_foreground
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Rounded avatar
        AppAvatar(
            size = 120.dp,
            imagePainter = painterResource(id = imageRes),
            contentDescription = "Profile Picture"
        )

        Spacer(modifier = modifier.height(24.dp))

        // Name
        TitleXL(text = name)

        Spacer(modifier = modifier.height(16.dp))

        // Email
        BodyM(
            text = email,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AboutScreenLight() {
    LocarooTheme(darkTheme = false, dynamicColor = false) {
        Surface {
            AboutScreen(
                name = "Nurhaq Halim",
                email = "halimjr11@gmail.com"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AboutScreenDark() {
    LocarooTheme(darkTheme = true, dynamicColor = false) {
        Surface {
            AboutScreen(
                name = "Nurhaq Halim",
                email = "halimjr11@gmail.com"
            )
        }
    }
}

