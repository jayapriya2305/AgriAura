package com.priya.agriaura.network

data class SoilRecommendationResponse(
    val success: Boolean,
    val data: List<RecommendedCrop>
)