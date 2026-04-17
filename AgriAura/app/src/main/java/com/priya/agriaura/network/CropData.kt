package com.priya.agriaura.network


data class CropData(
    val id: Int,
    val crop_name: String,
    val soil_type: String,
    val season: String,
    val water_sunlight: String,
    val fertilizer: String,
    val image_name: String
)