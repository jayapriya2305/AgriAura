package com.priya.agriaura

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plants")
data class PlantEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val scientificName: String,
    val location: String,
    val status: String,
    val imageUrl: String,
    val waterInDays: Int
)
