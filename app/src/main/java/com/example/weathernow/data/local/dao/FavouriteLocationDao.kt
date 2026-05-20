package com.example.weathernow.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weathernow.data.local.entity.FavouriteLocationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteLocationDao {

    @Query("SELECT * FROM favourite_locations ORDER BY cityName ASC")
    fun getFavourites(): Flow<List<FavouriteLocationEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavourite(entity: FavouriteLocationEntity)

    @Delete
    suspend fun removeFavourite(entity: FavouriteLocationEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM favourite_locations WHERE cityName = :cityName)")
    suspend fun isFavourite(cityName: String): Boolean
}