package com.example.weathernow.domain.model

data class Location(
    val cityName: String,
    val countryCode: String,
    val latitude: Double,
    val longitude: Double
)