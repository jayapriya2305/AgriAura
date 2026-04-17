package com.priya.agriaura.network



data class ResetPasswordRequest(
    val email: String,
    val new_password: String,
    val confirm_password: String
)
