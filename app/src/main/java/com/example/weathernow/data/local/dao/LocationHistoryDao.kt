package com.example.weathernow.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weathernow.data.local.entity.LocationHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationHistoryDao {

    @Query("SELECT * FROM location_history ORDER BY searchedAtEpoch DESC LIMIT 10")
    fun getHistory(): Flow<List<LocationHistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(entity: LocationHistoryEntity)

    @Query(
        "DELETE FROM location_history WHERE id NOT IN " +
                "(SELECT id FROM location_history ORDER BY searchedAtEpoch DESC LIMIT 10)"
    )
    suspend fun deleteOldLocations()
}