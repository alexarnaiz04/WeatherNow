package com.example.weathernow.data.remote.dto

import com.google.gson.annotations.SerializedName

data class WeatherResponseDto(
    @SerializedName("name") val cityName: String,
    @SerializedName("sys") val sys: SysDto,
    @SerializedName("main") val main: MainDto,
    @SerializedName("wind") val wind: WindDto,
    @SerializedName("weather") val weather: List<WeatherConditionDto>,
    @SerializedName("coord") val coord: CoordDto
)

data class SysDto(
    @SerializedName("country") val countryCode: String
)

data class MainDto(
    @SerializedName("temp") val temp: Double,
    @SerializedName("feels_like") val feelsLike: Double,
    @SerializedName("humidity") val humidity: Int
)

data class WindDto(
    @SerializedName("speed") val speed: Double
)

data class WeatherConditionDto(
    @SerializedName("description") val description: String,
    @SerializedName("icon") val icon: String
)

data class CoordDto(
    @SerializedName("lat") val lat: Double,
    @SerializedName("lon") val lon: Double
)