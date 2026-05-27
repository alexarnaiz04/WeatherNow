package com.example.weathernow.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

data class WeatherVisuals(
    val emoji: String,
    val label: String,
    val background: Brush,
    val accent: Color
)

fun getWeatherVisuals(condition: String): WeatherVisuals {
    val text = condition.lowercase()

    return when {
        text.contains("rain") || text.contains("drizzle") -> WeatherVisuals(
            emoji = "🌧",
            label = "Rainy",
            background = Brush.verticalGradient(
                listOf(Color(0xFF0F2027), Color(0xFF203A43), Color(0xFF2C5364))
            ),
            accent = Color(0xFF7DD3FC)
        )

        text.contains("cloud") || text.contains("overcast") -> WeatherVisuals(
            emoji = "☁",
            label = "Cloudy",
            background = Brush.verticalGradient(
                listOf(Color(0xFF485563), Color(0xFF29323C))
            ),
            accent = Color(0xFFCBD5E1)
        )

        text.contains("clear") || text.contains("sun") -> WeatherVisuals(
            emoji = "☀",
            label = "Sunny",
            background = Brush.verticalGradient(
                listOf(Color(0xFF4568DC), Color(0xFFB06AB3))
            ),
            accent = Color(0xFFFBBF24)
        )

        text.contains("snow") -> WeatherVisuals(
            emoji = "❄",
            label = "Snowy",
            background = Brush.verticalGradient(
                listOf(Color(0xFF83A4D4), Color(0xFFB6FBFF))
            ),
            accent = Color(0xFFE0F2FE)
        )

        text.contains("storm") || text.contains("thunder") -> WeatherVisuals(
            emoji = "⛈",
            label = "Stormy",
            background = Brush.verticalGradient(
                listOf(Color(0xFF232526), Color(0xFF414345))
            ),
            accent = Color(0xFFFACC15)
        )

        text.contains("mist") || text.contains("fog") || text.contains("haze") -> WeatherVisuals(
            emoji = "🌫",
            label = "Foggy",
            background = Brush.verticalGradient(
                listOf(Color(0xFF606C88), Color(0xFF3F4C6B))
            ),
            accent = Color(0xFFE5E7EB)
        )

        else -> WeatherVisuals(
            emoji = "🌤",
            label = "Weather",
            background = Brush.verticalGradient(
                listOf(Color(0xFF1E3C72), Color(0xFF2A5298))
            ),
            accent = Color(0xFF93C5FD)
        )
    }
}