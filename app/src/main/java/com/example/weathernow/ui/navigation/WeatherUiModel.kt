package com.example.weathernow.ui.navigation

data class WeatherUiModel(
    val city: String,
    val country: String,
    val temperature: String,
    val condition: String,
    val humidity: String,
    val wind: String,
    val feelsLike: String
)