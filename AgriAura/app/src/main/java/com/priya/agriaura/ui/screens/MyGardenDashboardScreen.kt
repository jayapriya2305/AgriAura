package com.priya.agriaura

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.priya.agriaura.model.Plant
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyGardenDashboardScreen(navController: NavController) {

    Box(modifier = Modifier.fillMaxSize()) {

        // 🌿 Background
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
                    title = { Text("My Garden 🌿") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.White.copy(alpha = 0.9f)
                    )
                )
            },

            // ✅ CORRECT FAB (WORKING)
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        navController.navigate("create_garden")
                    },
                    containerColor = Color(0xFF16C25D)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Plant")
                }
            }

        ) { padding ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {

                Text(
                    "Good Day 🌱",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Text(
                    "Take care of your plants today!",
                    color = Color.White.copy(alpha = 0.8f)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 🌤 Weather Card
                Card(
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF16C25D)
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.WbSunny,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text("24°C", color = Color.White, fontSize = 20.sp)
                            Text("Partly Cloudy", color = Color.White)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    "Your Plants",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(12.dp))

                if (myGarden.isEmpty()) {
                    EmptyGardenUI(navController)
                } else {
                    PlantGrid()
                }
            }
        }
    }
}

@Composable
fun EmptyGardenUI(navController: NavController) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("No plants yet 🌱", color = Color.White)

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                navController.navigate("create_garden")
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF16C25D)
            )
        ) {
            Icon(Icons.Default.Add, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Add Plant")
        }
    }
}

@Composable
fun PlantGrid() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(myGarden.size) { index ->
            PlantCard(myGarden[index])
        }
    }
}
fun getWateringText(plantingDate: String): String {

    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    val plantedDate = LocalDate.parse(plantingDate, formatter)
    val today = LocalDate.now()

    val daysSincePlant = ChronoUnit.DAYS.between(plantedDate, today)

    val wateringCycle = 2 // every 2 days

    val nextWaterIn = wateringCycle - (daysSincePlant % wateringCycle)

    return when (nextWaterIn.toInt()) {
        0 -> "Water today 💧"
        1 -> "Water tomorrow 💧"
        else -> "Water in $nextWaterIn days 💧"
    }
}

@Composable
fun PlantCard(plant: Plant) {

    Card(
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
            ) {

                // ✅ Plant Image (NOT background)
                Image(
                    painter = painterResource(id = plant.image),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                Text(
                    text = plant.name,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(8.dp)
                )
            }

            Column(modifier = Modifier.padding(10.dp)) {

                Text(
                    getWateringText(plant.plantingDate),
                    color = Color.Gray,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(6.dp))

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFFE8F5E9))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        "Healthy 🌿",
                        fontSize = 11.sp,
                        color = Color(0xFF2E7D32)
                    )
                }
            }
        }
    }
}