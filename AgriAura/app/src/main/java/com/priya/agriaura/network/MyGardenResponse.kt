package com.priya.agriaura.network
import com.priya.agriaura.model.Plant

data class MyGardenResponse(
    val status: String,
    val plants: List<Plant>
)
