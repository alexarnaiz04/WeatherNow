package com.example.weathernow.data.mapper

import com.example.weathernow.data.local.HistoryEntity
import com.example.weathernow.data.local.WeatherLocationEntity
import com.example.weathernow.ui.navigation.WeatherUiModel

fun WeatherLocationEntity.toUiModel(): WeatherUiModel {
    return WeatherUiModel(
        city = city,
        country = country,
        temperatureCelsius = temperatureCelsius,
        condition = condition,
        humidity = humidity,
        wind = wind,
        feelsLikeCelsius = feelsLikeCelsius,
        pressure = pressure,
        uvIndex = uvIndex,
        sunrise = sunrise,
        sunset = sunset
    )
}

fun HistoryEntity.toUiModel(): WeatherUiModel {
    return WeatherUiModel(
        city = city,
        country = country,
        temperatureCelsius = temperatureCelsius,
        condition = condition,
        humidity = humidity,
        wind = wind,
        feelsLikeCelsius = feelsLikeCelsius,
        pressure = pressure,
        uvIndex = uvIndex,
        sunrise = sunrise,
        sunset = sunset
    )
}

fun WeatherUiModel.toFavouriteEntity(): WeatherLocationEntity {
    return WeatherLocationEntity(
        city = city,
        country = country,
        temperatureCelsius = temperatureCelsius,
        condition = condition,
        humidity = humidity,
        wind = wind,
        feelsLikeCelsius = feelsLikeCelsius,
        pressure = pressure,
        uvIndex = uvIndex,
        sunrise = sunrise,
        sunset = sunset
    )
}

fun WeatherUiModel.toHistoryEntity(): HistoryEntity {
    return HistoryEntity(
        city = city,
        country = country,
        temperatureCelsius = temperatureCelsius,
        condition = condition,
        humidity = humidity,
        wind = wind,
        feelsLikeCelsius = feelsLikeCelsius,
        pressure = pressure,
        uvIndex = uvIndex,
        sunrise = sunrise,
        sunset = sunset,
        searchedAt = System.currentTimeMillis()
    )
}