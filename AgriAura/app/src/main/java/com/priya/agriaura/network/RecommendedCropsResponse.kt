package com.priya.agriaura.network

data class RecommendedCropsResponse(
    val success: Boolean,
    val data: List<RecommendedCrop>
)

data class RecommendedCrop(
    val crop_name: String,
    val image_name: String
)
