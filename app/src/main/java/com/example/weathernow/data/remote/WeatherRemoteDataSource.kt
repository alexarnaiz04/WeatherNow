package com.example.weathernow.data.remote

import com.example.weathernow.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherRemoteDataSource {

    private val api: WeatherApiService = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WeatherApiService::class.java)

    suspend fun searchWeather(city: String): WeatherResponse {
        return api.getCurrentWeather(
            city = city,
            apiKey = BuildConfig.OWM_API_KEY
        )
    }

    suspend fun getWeatherByCoordinates(
        latitude: Double,
        longitude: Double
    ): WeatherResponse {
        return api.getCurrentWeatherByCoordinates(
            latitude = latitude,
            longitude = longitude,
            apiKey = BuildConfig.OWM_API_KEY
        )
    }

    suspend fun getForecast(
        city: String
    ): ForecastResponse {
        return api.getForecast(
            city = city,
            apiKey = BuildConfig.OWM_API_KEY
        )
    }
}