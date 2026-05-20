package com.example.weathernow.data.repository

import com.example.weathernow.data.mapper.toCurrentWeather
import com.example.weathernow.data.mapper.toForecastItem
import com.example.weathernow.data.remote.WeatherApiService
import com.example.weathernow.domain.model.CurrentWeather
import com.example.weathernow.domain.model.ForecastItem
import com.example.weathernow.domain.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val apiService: WeatherApiService
) : WeatherRepository {

    override suspend fun getCurrentWeather(cityName: String): Result<CurrentWeather> {
        return try {
            val response = apiService.getCurrentWeather(cityName)

            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.toCurrentWeather())
            } else {
                Result.failure(Exception("Error fetching current weather"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCurrentWeatherByCoords(
        latitude: Double,
        longitude: Double
    ): Result<CurrentWeather> {
        return try {
            val response = apiService.getCurrentWeatherByCoords(latitude, longitude)

            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.toCurrentWeather())
            } else {
                Result.failure(Exception("Error fetching current weather by coordinates"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getForecast(cityName: String): Result<List<ForecastItem>> {
        return try {
            val response = apiService.getForecast(cityName)

            if (response.isSuccessful && response.body() != null) {
                val forecast = response.body()!!.items.map { it.toForecastItem() }
                Result.success(forecast)
            } else {
                Result.failure(Exception("Error fetching forecast"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}