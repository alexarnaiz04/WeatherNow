package com.example.weathernow.domain.repository

import com.example.weathernow.domain.model.CurrentWeather
import com.example.weathernow.domain.model.Location
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    fun getHistory(): Flow<List<Location>>

    suspend fun saveToHistory(weather: CurrentWeather)

    fun getFavourites(): Flow<List<Location>>

    suspend fun addFavourite(weather: CurrentWeather)

    suspend fun removeFavourite(cityName: String)

    suspend fun isFavourite(cityName: String): Boolean
}