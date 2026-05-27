package com.example.weathernow.data.mapper

import com.example.weathernow.data.remote.ForecastResponse
import com.example.weathernow.ui.navigation.ForecastUiModel

fun ForecastResponse.toForecastUiModels(): List<ForecastUiModel> {

    return items.take(8).map {

        ForecastUiModel(
            time = extractHour(it.dateText),
            temperature = it.main.temp.toInt(),
            condition = it.weather.firstOrNull()?.main ?: "Clear"
        )
    }
}

private fun extractHour(value: String): String {

    return try {
        value.substring(11, 16)
    } catch (e: Exception) {
        "--:--"
    }
}