package com.priya.agriaura.network


data class PlanSpaceRequest(
    val user_id: Int,
    val space_type: String,
    val length: Float,
    val width: Float,
    val height: Float = 0f,
    val unit: String
)
