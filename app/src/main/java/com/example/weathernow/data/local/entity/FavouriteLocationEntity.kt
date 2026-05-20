package com.example.weathernow.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_locations")
data class FavouriteLocationEntity(
    @PrimaryKey
    val cityName: String,
    val countryCode: String,
    val latitude: Double,
    val longitude: Double
)