package com.priya.agriaura.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.priya.agriaura.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward

/**
 * Simple result screen for soil recommendation.
 * This is a frontend stub that uses a tiny local rule engine (generateSimpleRecommendations).
 * It displays a list of recommended crop names with thumbnails.
 */
@Composable
fun SoilRecommendationResultScreen(
    navController: NavHostController,
    ph: Float,
    district: String,
    texture: String
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Background image - uses crops_bg which you already have
        Image(
            painter = painterResource(id = R.drawable.crops_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // dark overlay for readability
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xAA0A3A23))
        )

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
        ) {
            // Header row with back button
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Recommendations",
                    fontSize = 20.sp,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Info summary
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.95f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(text = "pH: $ph", fontSize = 14.sp, color = Color.Black)
                    Text(text = "District: $district", fontSize = 14.sp, color = Color.Black)
                    Text(text = "Texture: $texture", fontSize = 14.sp, color = Color.Black)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Recommendations list
            val recommended = generateSimpleRecommendations(ph, texture)
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxSize()) {
                items(recommended) { crop ->
                    RecommendationRow(cropName = crop)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Optional action: go to full crops screen
            Button(
                onClick = { navController.navigate("recommended_crops") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(text = "See All Crops")
            }
        }
    }
}

@Composable
private fun RecommendationRow(cropName: String) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // thumbnail: try to match common drawables (fallback to crop_tomato)
            val thumbId = when (cropName.lowercase()) {
                "tomato" -> R.drawable.crop_tomato
                "carrot" -> R.drawable.crop_carrot
                "paddy", "rice" -> R.drawable.crop_paddy
                "potato" -> R.drawable.crop_potato
                "spinach" -> R.drawable.crop_spinach
                "wheat" -> R.drawable.crop_wheat
                "onion" -> R.drawable.crop_onion
                "cabbage" -> R.drawable.crop_cabbage
                "banana" -> R.drawable.crop_banana
                "garlic" -> R.drawable.crop_garlic
                "turmeric" -> R.drawable.crop_turmeric
                "raddish", "radish" -> R.drawable.crop_raddish
                "brinjal" -> R.drawable.crop_brinjal
                "sugarcane" -> R.drawable.crop_sugarcane
                else -> R.drawable.crop_tomato
            }

            Image(
                painter = painterResource(id = thumbId),
                contentDescription = cropName,
                modifier = Modifier.size(64.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(text = cropName, fontSize = 16.sp, color = Color.Black)
                Text(text = "Suitable crop", fontSize = 12.sp, color = Color.Gray)
            }

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                imageVector = Icons.Filled.ArrowForward,
                contentDescription = "more",
                tint = Color.Gray
            )
        }
    }
}

/**
 * Basic rules for demonstration only.
 * Replace with backend logic later.
 */
private fun generateSimpleRecommendations(ph: Float, texture: String): List<String> {
    val list = mutableListOf<String>()
    // simple rules — refine these for real recommendations
    if (ph in 6.0..7.5 && texture.equals("Loamy", ignoreCase = true)) {
        list.add("Tomato")
        list.add("Spinach")
        list.add("Carrot")
    } else if (ph < 6.0) {
        list.add("Paddy")
        list.add("Sugarcane")
        list.add("Turmeric")
    } else if (ph > 7.5) {
        list.add("Wheat")
        list.add("Onion")
        list.add("Brinjal")
    } else {
        list.add("Potato")
        list.add("Cabbage")
    }
    return list
}
