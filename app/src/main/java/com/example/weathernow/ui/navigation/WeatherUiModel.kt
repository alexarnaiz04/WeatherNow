package com.example.weathernow.ui.navigation

data class WeatherUiModel(
    val city: String,
    val country: String,
    val temperature: String,
    val condition: String,
    val humidity: String,
    val wind: String,
    val feelsLike: String,
    val pressure: String = "1012 hPa",
    val uvIndex: String = "Moderate",
    val sunrise: String = "06:12",
    val sunset: String = "20:45"
)