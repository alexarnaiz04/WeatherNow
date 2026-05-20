package com.example.weathernow.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weathernow.data.local.dao.FavouriteLocationDao
import com.example.weathernow.data.local.dao.LocationHistoryDao
import com.example.weathernow.data.local.entity.FavouriteLocationEntity
import com.example.weathernow.data.local.entity.LocationHistoryEntity

@Database(
    entities = [
        LocationHistoryEntity::class,
        FavouriteLocationEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun locationHistoryDao(): LocationHistoryDao

    abstract fun favouriteLocationDao(): FavouriteLocationDao
}