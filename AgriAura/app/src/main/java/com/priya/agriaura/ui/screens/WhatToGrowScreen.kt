package com.priya.agriaura.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.priya.agriaura.R
import androidx.compose.foundation.lazy.grid.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WhatToGrowScreen(
    navController: NavController,
    planSpace: String,
    selectedContainerTypes: List<String>
) {

    var selectedCategories by remember { mutableStateOf(setOf<String>()) }

    val categories = listOf(
        Triple("Vegetables", R.drawable.vegetables, "Tomato, Chili, Brinjal"),
        Triple("Herbs", R.drawable.herbs, "Mint, Basil, Coriander"),
        Triple("Flowers", R.drawable.flowers, "Rose, Jasmine"),
        Triple("Fruits", R.drawable.fruits, "Apple, Mango, Banana")
    )

    Box(modifier = Modifier.fillMaxSize()) {

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
                    title = { Text("What to Grow", color = Color.White) },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = Color.White)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                )
            },

            bottomBar = {

                val categoryString = selectedCategories.joinToString(",")
                val containerString = selectedContainerTypes.joinToString(",")

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black.copy(alpha = 0.3f))
                ) {

                    Text(
                        text = "${selectedCategories.size} categories selected",
                        color = Color.White,
                        modifier = Modifier.padding(16.dp)
                    )

                    Button(
                        onClick = {
                            navController.navigate(
                                "plant_recommendations/$planSpace/$categoryString/$containerString"
                            )
                        },
                        enabled = selectedCategories.isNotEmpty(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF16C25D))
                    ) {
                        Text("See Suitable Plants", fontWeight = FontWeight.Bold)
                    }
                }
            }

        ) { paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {

                Text(
                    "What would you like to grow?",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(categories) { (title, image, subtitle) ->

                        CategoryCardNew(
                            title = title,
                            subtitle = subtitle,
                            image = image,
                            isSelected = selectedCategories.contains(title),
                            onClick = {
                                selectedCategories =
                                    if (selectedCategories.contains(title))
                                        selectedCategories - title
                                    else
                                        selectedCategories + title
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryCardNew(
    title: String,
    subtitle: String,
    image: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        border = if (isSelected) BorderStroke(2.dp, Color(0xFF16C25D)) else null,
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.95f)
        )
    ) {
        Column {

            Image(
                painter = painterResource(id = image),
                contentDescription = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(12.dp)) {
                Text(title, fontWeight = FontWeight.Bold)
                Text(subtitle, fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}