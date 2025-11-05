package com.halimjr11.locaroo.view.screens.about

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    modifier: Modifier = Modifier,
    name: String = "Nurhaq Halim",
    email: String = "nurhaqhalim11@gmail.com",
    imageRes: Int = R.drawable.foto
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CenterAlignedTopAppBar(
            modifier = modifier.background(MaterialTheme.colorScheme.primary),
            windowInsets = WindowInsets(0),
            title = {
                Text(text = "About")
            }
        )
        // Rounded avatar
        AppAvatar(
            modifier = modifier.padding(top = 24.dp),
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
            AboutScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AboutScreenDark() {
    LocarooTheme(darkTheme = true, dynamicColor = false) {
        Surface {
            AboutScreen()
        }
    }
}

