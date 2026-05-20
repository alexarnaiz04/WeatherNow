package com.example.weathernow.data.remote

import com.example.weathernow.data.remote.dto.ForecastResponseDto
import com.example.weathernow.data.remote.dto.WeatherResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("q") cityName: String,
        @Query("units") units: String = "metric",
        @Query("appid") apiKey: String = "demo_key"
    ): Response<WeatherResponseDto>

    @GET("weather")
    suspend fun getCurrentWeatherByCoords(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String = "metric",
        @Query("appid") apiKey: String = "demo_key"
    ): Response<WeatherResponseDto>

    @GET("forecast")
    suspend fun getForecast(
        @Query("q") cityName: String,
        @Query("units") units: String = "metric",
        @Query("cnt") count: Int = 40,
        @Query("appid") apiKey: String = "demo_key"
    ): Response<ForecastResponseDto>
}