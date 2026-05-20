package com.example.weathernow.data.repository

import com.example.weathernow.data.local.dao.FavouriteLocationDao
import com.example.weathernow.data.local.dao.LocationHistoryDao
import com.example.weathernow.data.local.entity.FavouriteLocationEntity
import com.example.weathernow.data.mapper.toFavouriteEntity
import com.example.weathernow.data.mapper.toHistoryEntity
import com.example.weathernow.data.mapper.toLocation
import com.example.weathernow.domain.model.CurrentWeather
import com.example.weathernow.domain.model.Location
import com.example.weathernow.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocationRepositoryImpl(
    private val historyDao: LocationHistoryDao,
    private val favouriteDao: FavouriteLocationDao
) : LocationRepository {

    override fun getHistory(): Flow<List<Location>> {
        return historyDao.getHistory().map { list ->
            list.map { it.toLocation() }
        }
    }

    override suspend fun saveToHistory(weather: CurrentWeather) {
        historyDao.insertLocation(weather.toHistoryEntity())
        historyDao.deleteOldLocations()
    }

    override fun getFavourites(): Flow<List<Location>> {
        return favouriteDao.getFavourites().map { list ->
            list.map { it.toLocation() }
        }
    }

    override suspend fun addFavourite(weather: CurrentWeather) {
        favouriteDao.addFavourite(weather.toFavouriteEntity())
    }

    override suspend fun removeFavourite(cityName: String) {
        val entity = FavouriteLocationEntity(
            cityName = cityName,
            countryCode = "",
            latitude = 0.0,
            longitude = 0.0
        )
        favouriteDao.removeFavourite(entity)
    }

    override suspend fun isFavourite(cityName: String): Boolean {
        return favouriteDao.isFavourite(cityName)
    }
}