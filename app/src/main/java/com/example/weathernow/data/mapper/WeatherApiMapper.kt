package com.example.weathernow.data.mapper

import com.example.weathernow.data.remote.WeatherResponse
import com.example.weathernow.ui.navigation.WeatherUiModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun WeatherResponse.toUiModel(): WeatherUiModel {
    val condition = weather.firstOrNull()?.description
        ?.replaceFirstChar { it.uppercase() }
        ?: "Unknown"

    return WeatherUiModel(
        city = name,
        country = sys.country,
        temperatureCelsius = main.temp.toInt(),
        condition = condition,
        humidity = "${main.humidity}%",
        wind = "${wind.speed} m/s",
        feelsLikeCelsius = main.feelsLike.toInt(),
        pressure = "${main.pressure} hPa",
        uvIndex = "Not available",
        sunrise = formatUnixTime(sys.sunrise),
        sunset = formatUnixTime(sys.sunset)
    )
}

private fun formatUnixTime(value: Long): String {
    val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    return formatter.format(Date(value * 1000))
}