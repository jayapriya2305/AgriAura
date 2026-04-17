package com.priya.agriaura.network



data class AddPlantRequest(
    val user_id: Int,
    val plant_id: Int,
    val plant_name: String,
    val category: String,
    val area: String,
    val container: String,
    val planting_date: String,
    val light_requirement: String,
    val care_level: String
)
