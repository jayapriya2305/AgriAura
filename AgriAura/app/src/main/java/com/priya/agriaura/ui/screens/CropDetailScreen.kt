package com.priya.agriaura.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.priya.agriaura.network.cropDetailsList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CropDetailScreen(
    navController: NavHostController,
    cropName: String?,
    imageRes: Int
) {
    val crop = cropDetailsList.find { it.name == cropName }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(crop?.name ?: "Crop Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFE8F5E9)
                )
            )
        }
    ) {
        if (crop != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .verticalScroll(rememberScrollState())
            ) {
                Image(
                    painter = painterResource(id = crop.imageRes),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    contentScale = ContentScale.Crop
                )

                Column(modifier = Modifier.padding(16.dp)) {
                    Text(crop.name, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(crop.description, fontSize = 16.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.height(16.dp))

                    InfoRow(label = "Soil Type", value = crop.soilType)
                    InfoRow(label = "Climate", value = crop.climate)
                    InfoRow(label = "Best Months to Grow", value = crop.bestMonths)
                    InfoRow(label = "Water Needs", value = crop.waterNeeds)
                    InfoRow(label = "Fertilizer", value = crop.fertilizer)
                }
            }
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Crop not found")
            }
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(0.4f)
        )
        Text(
            text = value,
            modifier = Modifier.weight(0.6f)
        )
    }
}
