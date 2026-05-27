package com.example.weathernow.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Thunderstorm
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class WeatherConditionStyle(
    val backgroundTop: Color,
    val backgroundBottom: Color,
    val icon: ImageVector
)

fun getWeatherStyle(condition: String): WeatherConditionStyle {
    return when {
        condition.contains("sun", ignoreCase = true) ||
                condition.contains("clear", ignoreCase = true) -> {
            WeatherConditionStyle(
                backgroundTop = Color(0xFFFFD54F),
                backgroundBottom = Color(0xFFFFF8E1),
                icon = Icons.Default.Cloud
            )
        }

        condition.contains("rain", ignoreCase = true) -> {
            WeatherConditionStyle(
                backgroundTop = Color(0xFF90CAF9),
                backgroundBottom = Color(0xFFE3F2FD),
                icon = Icons.Default.WaterDrop
            )
        }

        condition.contains("storm", ignoreCase = true) -> {
            WeatherConditionStyle(
                backgroundTop = Color(0xFF78909C),
                backgroundBottom = Color(0xFFECEFF1),
                icon = Icons.Default.Thunderstorm
            )
        }

        else -> {
            WeatherConditionStyle(
                backgroundTop = Color(0xFFB3E5FC),
                backgroundBottom = Color(0xFFF5F5F5),
                icon = Icons.Default.Cloud
            )
        }
    }
}