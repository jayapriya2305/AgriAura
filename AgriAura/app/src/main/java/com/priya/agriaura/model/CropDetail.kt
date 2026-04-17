package com.priya.agriaura.model

import androidx.annotation.DrawableRes

data class CropDetail(
    val name: String,
    @DrawableRes val imageRes: Int,
    val description: String,
    val soilType: String,
    val climate: String,
    val bestMonths: String,
    val waterNeeds: String,
    val fertilizer: String
)
