package com.example.weathernow.data.mapper

import com.example.weathernow.data.local.entity.FavouriteLocationEntity
import com.example.weathernow.data.local.entity.LocationHistoryEntity
import com.example.weathernow.data.remote.dto.ForecastItemDto
import com.example.weathernow.data.remote.dto.WeatherResponseDto
import com.example.weathernow.domain.model.CurrentWeather
import com.example.weathernow.domain.model.ForecastItem
import com.example.weathernow.domain.model.Location

fun WeatherResponseDto.toCurrentWeather(): CurrentWeather {
    val firstCondition = weather.firstOrNull()

    return CurrentWeather(
        cityName = cityName,
        countryCode = sys.countryCode,
        temperatureCelsius = main.temp,
        feelsLikeCelsius = main.feelsLike,
        humidity = main.humidity,
        windSpeedMs = wind.speed,
        conditionText = firstCondition?.description ?: "Unknown",
        iconCode = firstCondition?.icon ?: "",
        latitude = coord.lat,
        longitude = coord.lon
    )
}

fun ForecastItemDto.toForecastItem(): ForecastItem {
    val firstCondition = weather.firstOrNull()

    return ForecastItem(
        dateTimeEpoch = dateTimeEpoch,
        temperatureCelsius = main.temp,
        conditionText = firstCondition?.description ?: "Unknown",
        iconCode = firstCondition?.icon ?: ""
    )
}

fun LocationHistoryEntity.toLocation(): Location {
    return Location(
        cityName = cityName,
        countryCode = countryCode,
        latitude = latitude,
        longitude = longitude
    )
}

fun FavouriteLocationEntity.toLocation(): Location {
    return Location(
        cityName = cityName,
        countryCode = countryCode,
        latitude = latitude,
        longitude = longitude
    )
}

fun CurrentWeather.toHistoryEntity(): LocationHistoryEntity {
    return LocationHistoryEntity(
        cityName = cityName,
        countryCode = countryCode,
        latitude = latitude,
        longitude = longitude,
        searchedAtEpoch = System.currentTimeMillis() / 1000
    )
}

fun CurrentWeather.toFavouriteEntity(): FavouriteLocationEntity {
    return FavouriteLocationEntity(
        cityName = cityName,
        countryCode = countryCode,
        latitude = latitude,
        longitude = longitude
    )
}