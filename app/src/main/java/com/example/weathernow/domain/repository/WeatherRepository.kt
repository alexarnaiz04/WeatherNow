package com.example.weathernow.domain.repository

import com.example.weathernow.domain.model.CurrentWeather
import com.example.weathernow.domain.model.ForecastItem

interface WeatherRepository {
    suspend fun getCurrentWeather(cityName: String): Result<CurrentWeather>

    suspend fun getCurrentWeatherByCoords(
        latitude: Double,
        longitude: Double
    ): Result<CurrentWeather>

    suspend fun getForecast(cityName: String): Result<List<ForecastItem>>
}