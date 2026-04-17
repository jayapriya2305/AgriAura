package com.priya.agriaura.theme.terrace

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.priya.agriaura.R
import com.priya.agriaura.model.Plant
import com.priya.agriaura.model.plantList
import com.priya.agriaura.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlantRecommendationScreen(
    navController: NavController,
    selectedSpace: String,
    selectedCategory: String,
    selectedContainer: String
) {

    val viewModel: AuthViewModel = viewModel()

    val selectedCategoriesList = remember(selectedCategory) {
        selectedCategory.split(",").map { it.trim() }.filter { it.isNotEmpty() }
    }

    val finalPlants = remember(selectedCategoriesList) {
        val filtered = plantList.filter {
            selectedCategoriesList.contains(it.category)
        }
        if (filtered.isNotEmpty()) filtered.take(10)
        else plantList.shuffled().take(8)
    }

    Box(modifier = Modifier.fillMaxSize()) {

        // 🌿 BACKGROUND
        Image(
            painter = painterResource(id = R.drawable.terrace_plants_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = { Text("Plant Recommendations", color = Color.White) },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = Color.White)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            }
        ) { paddingValues ->

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(finalPlants) { plant ->
                    PlantRecommendationCard(
                        plant = plant,
                        onAddClick = {
                            navController.navigate("add_plant/${plant.name}/${selectedSpace}")
                        }
                    )
                }
            }
        }
    }
}
@Composable
fun PlantRecommendationCard(
    plant: Plant,
    onAddClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.9f)
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(id = plant.image),
                contentDescription = plant.name,
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {

                Text(plant.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)

                Text(
                    plant.lightRequirement,
                    color = Color.Gray,
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                CareLevelTag(plant.careLevel)
            }

            IconButton(onClick = onAddClick) {
                Icon(Icons.Default.Add, contentDescription = null, tint = Color(0xFF16C25D))
            }
        }
    }
}
@Composable
fun CareLevelTag(level: String) {

    val bg = if (level == "Easy") Color(0xFFE9F8E8) else Color(0xFFFFF3E0)
    val fg = if (level == "Easy") Color(0xFF16C25D) else Color(0xFFFF9800)

    Surface(
        shape = RoundedCornerShape(8.dp),
        color = bg
    ) {
        Text(
            text = level,
            color = fg,
            fontSize = 12.sp,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
    }
}