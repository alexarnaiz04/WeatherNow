package com.example.weathernow.data.remote

import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    @SerializedName("list")
    val items: List<ForecastItemResponse>
)

data class ForecastItemResponse(
    @SerializedName("dt_txt")
    val dateText: String,

    val main: ForecastMainResponse,

    val weather: List<ForecastWeatherResponse>
)

data class ForecastMainResponse(
    val temp: Double
)

data class ForecastWeatherResponse(
    val main: String,
    val description: String
)