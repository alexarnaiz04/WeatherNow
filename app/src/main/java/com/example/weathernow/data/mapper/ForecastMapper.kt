package com.example.weathernow.data.mapper

import com.example.weathernow.data.remote.ForecastResponse
import com.example.weathernow.ui.navigation.ForecastUiModel
import kotlin.math.roundToInt

fun ForecastResponse.toForecastUiModels(): List<ForecastUiModel> {
    return items
        .take(8)
        .map { item ->
            ForecastUiModel(
                time = item.dateText.extractHour(),
                temperature = item.main.temp.roundToInt(),
                condition = item.weather.firstOrNull()?.main ?: "Unknown"
            )
        }
}

private fun String.extractHour(): String {
    return try {
        substring(11, 16)
    } catch (e: Exception) {
        "--:--"
    }
}