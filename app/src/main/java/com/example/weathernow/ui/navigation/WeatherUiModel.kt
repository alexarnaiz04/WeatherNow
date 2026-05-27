package com.example.weathernow.ui.navigation

data class WeatherUiModel(
    val city: String,
    val country: String,
    val temperatureCelsius: Int,
    val condition: String,
    val humidity: String,
    val wind: String,
    val feelsLikeCelsius: Int,
    val pressure: String = "1012 hPa",
    val uvIndex: String = "Moderate",
    val sunrise: String = "06:12",
    val sunset: String = "20:45"
) {
    fun temperatureText(useFahrenheit: Boolean): String {
        return if (useFahrenheit) {
            "${temperatureCelsius * 9 / 5 + 32}°F"
        } else {
            "$temperatureCelsius°C"
        }
    }

    fun feelsLikeText(useFahrenheit: Boolean): String {
        return if (useFahrenheit) {
            "${feelsLikeCelsius * 9 / 5 + 32}°F"
        } else {
            "$feelsLikeCelsius°C"
        }
    }
}