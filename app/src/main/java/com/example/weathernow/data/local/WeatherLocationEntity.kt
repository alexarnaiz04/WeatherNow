package com.example.weathernow.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_locations")
data class WeatherLocationEntity(
    @PrimaryKey val city: String,
    val country: String,
    val temperature: String,
    val condition: String,
    val humidity: String,
    val wind: String,
    val feelsLike: String,
    val pressure: String,
    val uvIndex: String,
    val sunrise: String,
    val sunset: String
)

@Entity(tableName = "search_history")
data class HistoryEntity(
    @PrimaryKey val city: String,
    val country: String,
    val temperature: String,
    val condition: String,
    val humidity: String,
    val wind: String,
    val feelsLike: String,
    val pressure: String,
    val uvIndex: String,
    val sunrise: String,
    val sunset: String,
    val searchedAt: Long
)