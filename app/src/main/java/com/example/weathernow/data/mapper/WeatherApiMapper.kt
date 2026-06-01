package com.example.weathernow.data.mapper

import com.example.weathernow.data.remote.WeatherResponse
import com.example.weathernow.ui.navigation.WeatherUiModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

fun WeatherResponse.toUiModel(): WeatherUiModel {
    val condition = weather.firstOrNull()?.description
        ?.replaceFirstChar { it.uppercase() }
        ?: "Unknown"

    return WeatherUiModel(
        city = name,
        country = sys.country,
        temperatureCelsius = main.temp.roundToInt(),
        condition = condition,
        humidity = "${main.humidity}%",
        wind = "${(wind.speed * 3.6).roundToInt()} km/h",
        feelsLikeCelsius = main.feelsLike.roundToInt(),
        pressure = "${main.pressure} hPa",
        uvIndex = "Not available",
        sunrise = formatUnixTime(sys.sunrise),
        sunset = formatUnixTime(sys.sunset)
    )
}

private fun formatUnixTime(timestamp: Long): String {
    return try {
        val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
        formatter.format(Date(timestamp * 1000))
    } catch (e: Exception) {
        "--:--"
    }
}