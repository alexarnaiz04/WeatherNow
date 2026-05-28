package com.example.weathernow.data.remote

import com.example.weathernow.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherRemoteDataSource {

    private val apiKey = BuildConfig.OWM_API_KEY.trim()

    private val api: WeatherApiService = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WeatherApiService::class.java)

    suspend fun searchWeather(city: String): WeatherResponse {
        checkApiKey()

        return api.getCurrentWeather(
            city = city.trim(),
            apiKey = apiKey
        )
    }

    suspend fun getWeatherByCoordinates(
        latitude: Double,
        longitude: Double
    ): WeatherResponse {
        checkApiKey()

        return api.getCurrentWeatherByCoordinates(
            latitude = latitude,
            longitude = longitude,
            apiKey = apiKey
        )
    }

    suspend fun getForecast(city: String): ForecastResponse {
        checkApiKey()

        return api.getForecast(
            city = city.trim(),
            apiKey = apiKey
        )
    }

    private fun checkApiKey() {
        if (apiKey.isBlank()) {
            error("OpenWeather API key is empty. Check gradle.properties.")
        }
    }
}