package com.example.weathernow.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = WeatherBlue,
    primaryContainer = WeatherLightBlue,
    secondary = WeatherSky,
    background = WeatherBackground,
    surface = WeatherCard,
    onPrimary = WeatherCard,
    onBackground = WeatherDark,
    onSurface = WeatherDark
)

@Composable
fun WeatherNowTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColors,
        content = content
    )
}