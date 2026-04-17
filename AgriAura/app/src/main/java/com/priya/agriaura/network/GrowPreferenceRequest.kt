package com.priya.agriaura.network


data class GrowPreferenceRequest(
    val user_id: Int,
    val plan_space: String,
    val container_types: String,
    val plant_categories: String
)
