package com.example.weathernow.data.repository

import com.example.weathernow.data.remote.WeatherRemoteDataSource
import com.example.weathernow.data.remote.WeatherResponse

class WeatherRepository {

    private val remoteDataSource = WeatherRemoteDataSource()

    suspend fun searchWeather(city: String): WeatherResponse {
        return remoteDataSource.searchWeather(city)
    }
}