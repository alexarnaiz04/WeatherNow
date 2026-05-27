package com.example.weathernow.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Query("SELECT * FROM favourite_locations ORDER BY city ASC")
    fun getFavourites(): Flow<List<WeatherLocationEntity>>

    @Upsert
    suspend fun saveFavourite(weather: WeatherLocationEntity)

    @Query("DELETE FROM favourite_locations WHERE city = :city")
    suspend fun deleteFavourite(city: String)

    @Query("SELECT * FROM search_history ORDER BY searchedAt DESC")
    fun getHistory(): Flow<List<HistoryEntity>>

    @Upsert
    suspend fun saveHistory(weather: HistoryEntity)

    @Query("DELETE FROM search_history")
    suspend fun clearHistory()
}