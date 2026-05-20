package com.example.weathernow.domain.model

data class CurrentWeather(
    val cityName: String,
    val countryCode: String,
    val temperatureCelsius: Double,
    val feelsLikeCelsius: Double,
    val humidity: Int,
    val windSpeedMs: Double,
    val conditionText: String,
    val iconCode: String,
    val latitude: Double,
    val longitude: Double
)