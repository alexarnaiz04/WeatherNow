package com.example.weathernow.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ForecastResponseDto(
    @SerializedName("list") val items: List<ForecastItemDto>
)

data class ForecastItemDto(
    @SerializedName("dt") val dateTimeEpoch: Long,
    @SerializedName("main") val main: MainDto,
    @SerializedName("weather") val weather: List<WeatherConditionDto>
)