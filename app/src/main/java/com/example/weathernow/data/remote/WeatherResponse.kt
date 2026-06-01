package com.example.weathernow.data.remote

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    val name: String,
    val sys: SysResponse,
    val main: MainResponse,
    val weather: List<WeatherConditionResponse>,
    val wind: WindResponse
)

data class SysResponse(
    val country: String,
    val sunrise: Long,
    val sunset: Long
)

data class MainResponse(
    val temp: Double,
    @SerializedName("feels_like") val feelsLike: Double,
    val humidity: Int,
    val pressure: Int
)

data class WeatherConditionResponse(
    val main: String,
    val description: String,
    val icon: String
)

data class WindResponse(
    val speed: Double
)