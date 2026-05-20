package com.example.weathernow.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location_history")
data class LocationHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val cityName: String,
    val countryCode: String,
    val latitude: Double,
    val longitude: Double,
    val searchedAtEpoch: Long
)