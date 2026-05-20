package com.example.weathernow.domain.model

data class ForecastItem(
    val dateTimeEpoch: Long,
    val temperatureCelsius: Double,
    val conditionText: String,
    val iconCode: String
)