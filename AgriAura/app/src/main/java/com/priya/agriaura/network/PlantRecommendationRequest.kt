package com.priya.agriaura.network

data class PlantRecommendationRequest(
    val plan_space: String,
    val plant_category: String,
    val container_type: String
)
