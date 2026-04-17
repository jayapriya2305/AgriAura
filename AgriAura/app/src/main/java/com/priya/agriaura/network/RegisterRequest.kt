package com.priya.agriaura.network

data class RegisterRequest(
    val name: String,
    val username: String,
    val email: String,
    val phone: String,
    val password: String,
    val confirm_password: String
)
