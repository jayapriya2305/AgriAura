package com.priya.agriaura.network

data class ContainerRequest(
    val user_id: Int,
    val plan_space: String,
    val area: Float,
    val container_types: String
)
