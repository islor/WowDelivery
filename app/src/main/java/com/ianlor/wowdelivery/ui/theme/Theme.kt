package com.ianlor.wowdelivery.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Color.White,
    background = DarkGray,
    onBackground = Color.White,
    surface = LightBlue,
    onSurface = DarkGray
)

private val LightColorPalette = darkColors(
    primary = DarkGray,
    background = Color.White,
    onBackground = DarkGray,
    surface = LightBlue,
    onSurface = DarkGray,
    secondary = LightGreen
)

@Composable
fun WowDeliveryTheme(darkTheme: Boolean = true, content: @Composable() () -> Unit) {
    MaterialTheme(
        colors = LightColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}