package com.priya.agriaura

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PlantDao {
    @Insert
    suspend fun insertPlant(plant: PlantEntity)

    @Query("SELECT * FROM plants")
    suspend fun getAllPlants(): List<PlantEntity>
}
