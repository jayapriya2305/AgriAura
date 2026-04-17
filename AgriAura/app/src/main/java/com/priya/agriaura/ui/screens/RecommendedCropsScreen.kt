package com.priya.agriaura.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import com.priya.agriaura.R


data class CropItem(
    val name: String,
    val imageRes: Int
)

private val cropsList = listOf(
    CropItem("Tomato", R.drawable.crop_tomato),
    CropItem("Carrot", R.drawable.crop_carrot),
    CropItem("Paddy", R.drawable.crop_paddy),
    CropItem("Potato", R.drawable.crop_potato),
    CropItem("Spinach", R.drawable.crop_spinach),
    CropItem("Wheat", R.drawable.crop_wheat),
    CropItem("Onion", R.drawable.crop_onion),
    CropItem("Cabbage", R.drawable.crop_cabbage),
    CropItem("Banana", R.drawable.crop_banana),
    CropItem("Garlic", R.drawable.crop_garlic),
    CropItem("Turmeric", R.drawable.crop_turmeric),
    CropItem("Raddish", R.drawable.crop_raddish),
    CropItem("Brinjal", R.drawable.crop_brinjal),
    CropItem("Sugarcane", R.drawable.crop_sugarcane),
)

@Composable
fun RecommendedCropsScreen(navController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize()) {

        // Background image
        Image(
            painter = painterResource(id = R.drawable.crops_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // subtle overlay so white cards pop
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x33FFFFFF))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Recommended crops",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color(0xFF1B1B1B)
            )

            Spacer(modifier = Modifier.height(14.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                cropsList.chunked(2).forEach { row ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        row.forEach { crop ->
                            CropCard(
                                crop = crop,
                                modifier = Modifier
                                    .weight(1f)
                                    .height(150.dp),
                                onClick = {
                                    val encodedName = URLEncoder.encode(crop.name, StandardCharsets.UTF_8.toString())
                                    navController.navigate("crop_detail/${encodedName}/${crop.imageRes}")
                                }
                            )
                        }

                        if (row.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }


            }
        }
    }

}

@Composable
private fun CropCard(
    crop: CropItem,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    val corner = 18.dp

    Card(
        modifier = modifier.then(
            if (onClick != null) Modifier.clickable { onClick() } else Modifier
        ),
        shape = RoundedCornerShape(corner),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            // IMAGE (only top rounded)
            Image(
                painter = painterResource(id = crop.imageRes),
                contentDescription = crop.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(95.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = corner,
                            topEnd = corner,
                            bottomStart = 0.dp,
                            bottomEnd = 0.dp
                        )
                    ),
                contentScale = ContentScale.Crop
            )

            // NAME AREA (NO rounded corners)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)        // adjust height if needed
                    .background(Color.White)  // keeps it square
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = crop.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF1B1B1B),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}