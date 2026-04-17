package com.priya.agriaura

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

// Data class for crop details including ideal conditions
data class CropSoilData(
    val name: String,
    val imageRes: Int,
    val idealPh: ClosedFloatingPointRange<Float>,
    val suitableTextures: List<String>
)

// Sample database of crops and their ideal soil conditions
private val allCrops = listOf(
    CropSoilData("Tomato", R.drawable.crop_tomato, 6.0f..6.8f, listOf("Loamy", "Sandy")),
    CropSoilData("Carrot", R.drawable.crop_carrot, 6.0f..7.0f, listOf("Sandy", "Loamy", "Peaty")),
    CropSoilData("Paddy", R.drawable.crop_paddy, 5.0f..6.5f, listOf("Clay", "Loamy")),
    CropSoilData("Potato", R.drawable.crop_potato, 4.8f..6.5f, listOf("Sandy", "Loamy", "Silty")),
    CropSoilData("Spinach", R.drawable.crop_spinach, 6.5f..7.5f, listOf("Loamy", "Silty")),
    CropSoilData("Wheat", R.drawable.crop_wheat, 6.0f..7.0f, listOf("Loamy")),
    CropSoilData("Onion", R.drawable.crop_onion, 6.0f..7.0f, listOf("Sandy", "Loamy")),
    CropSoilData("Cabbage", R.drawable.crop_cabbage, 6.0f..7.5f, listOf("Loamy", "Clay")),
    CropSoilData("Banana", R.drawable.crop_banana, 5.5f..6.5f, listOf("Loamy")),
    CropSoilData("Garlic", R.drawable.crop_garlic, 6.0f..7.0f, listOf("Loamy", "Sandy")),
    CropSoilData("Turmeric", R.drawable.crop_turmeric, 5.0f..7.5f, listOf("Sandy", "Loamy")),
    CropSoilData("Raddish", R.drawable.crop_raddish, 6.0f..7.0f, listOf("Sandy")),
    CropSoilData("Brinjal", R.drawable.crop_brinjal, 5.5f..6.5f, listOf("Silty", "Loamy")),
    CropSoilData("Sugarcane", R.drawable.crop_sugarcane, 6.0f..8.0f, listOf("Clay", "Loamy"))
)

@Composable
fun SoilRecommendationScreen(
    navController: NavController,
    phValue: Float,
    district: String,
    texture: String
) {
    // Filter crops based on pH and texture
    val recommendedCrops = remember(phValue, texture) {
        allCrops.filter { crop ->
            phValue in crop.idealPh && texture in crop.suitableTextures
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.soil_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x88000000))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Recommended Crops",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Based on pH $phValue, $texture texture in $district",
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (recommendedCrops.isEmpty()) {

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No suitable crops found for the selected conditions.",
                        color = Color.White,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(32.dp)
                    )
                }

            } else {

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(recommendedCrops) { crop ->
                        RecommendedCropCard(crop = crop)
                    }
                }
            }
        }
    }
}

@Composable
fun RecommendedCropCard(crop: CropSoilData) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.9f)
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {

            Image(
                painter = painterResource(id = crop.imageRes),
                contentDescription = crop.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp
                        )
                    )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = crop.name,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SoilRecommendationScreenPreview() {
    SoilRecommendationScreen(
        navController = rememberNavController(),
        phValue = 6.5f,
        district = "Vellore",
        texture = "Loamy"
    )
}
