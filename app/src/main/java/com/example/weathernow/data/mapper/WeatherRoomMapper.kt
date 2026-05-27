package com.example.weathernow.data.mapper

import com.example.weathernow.data.local.HistoryEntity
import com.example.weathernow.data.local.WeatherLocationEntity
import com.example.weathernow.ui.navigation.WeatherUiModel

fun WeatherUiModel.toFavouriteEntity(): WeatherLocationEntity {

    return WeatherLocationEntity(
        city = city,
        country = country,
        temperature = temperatureCelsius.toString(),
        condition = condition,
        humidity = humidity,
        wind = wind,
        feelsLike = feelsLikeCelsius.toString(),
        pressure = pressure,
        uvIndex = uvIndex,
        sunrise = sunrise,
        sunset = sunset
    )
}

fun WeatherLocationEntity.toUiModel(): WeatherUiModel {

    return WeatherUiModel(
        city = city,
        country = country,
        temperatureCelsius = temperature.toInt(),
        condition = condition,
        humidity = humidity,
        wind = wind,
        feelsLikeCelsius = feelsLike.toInt(),
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
        temperature = temperatureCelsius.toString(),
        condition = condition,
        humidity = humidity,
        wind = wind,
        feelsLike = feelsLikeCelsius.toString(),
        pressure = pressure,
        uvIndex = uvIndex,
        sunrise = sunrise,
        sunset = sunset,
        searchedAt = System.currentTimeMillis()
    )
}

fun HistoryEntity.toUiModel(): WeatherUiModel {

    return WeatherUiModel(
        city = city,
        country = country,
        temperatureCelsius = temperature.toInt(),
        condition = condition,
        humidity = humidity,
        wind = wind,
        feelsLikeCelsius = feelsLike.toInt(),
        pressure = pressure,
        uvIndex = uvIndex,
        sunrise = sunrise,
        sunset = sunset
    )
}