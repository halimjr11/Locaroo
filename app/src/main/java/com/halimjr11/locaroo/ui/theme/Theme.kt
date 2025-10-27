package com.halimjr11.locaroo.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color

// Brand color schemes
private val DarkColorScheme = darkColorScheme(
    primary = Terracotta,
    onPrimary = Color.White,
    primaryContainer = Terracotta.copy(alpha = 0.25f),
    onPrimaryContainer = Terracotta,

    secondary = LeafGreen,
    onSecondary = Color.White,
    secondaryContainer = LeafGreen.copy(alpha = 0.25f),
    onSecondaryContainer = LeafGreen,

    tertiary = WarmYellow,
    onTertiary = Color.Black,
    tertiaryContainer = WarmYellow.copy(alpha = 0.25f),
    onTertiaryContainer = WarmYellow,

    background = DarkBg,
    onBackground = Color(0xFFEDEDED),
    surface = DarkSurface,
    onSurface = Color(0xFFEDEDED),
    outline = DarkOutline,
    error = ErrorRed,
    onError = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = Terracotta,
    onPrimary = Color.White,
    primaryContainer = Terracotta.copy(alpha = 0.12f),
    onPrimaryContainer = Terracotta,

    secondary = LeafGreen,
    onSecondary = Color.White,
    secondaryContainer = LeafGreen.copy(alpha = 0.10f),
    onSecondaryContainer = LeafGreen,

    tertiary = WarmYellow,
    onTertiary = Color.Black,
    tertiaryContainer = WarmYellow.copy(alpha = 0.15f),
    onTertiaryContainer = WarmYellow,

    background = Neutral99,
    onBackground = Neutral10,
    surface = Neutral99,
    onSurface = Neutral10,
    outline = Neutral80,
    error = ErrorRed,
    onError = Color.White
)

@Composable
fun LocarooTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}