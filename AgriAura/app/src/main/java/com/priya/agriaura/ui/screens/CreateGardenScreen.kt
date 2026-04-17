package com.priya.agriaura.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.priya.agriaura.R
import com.priya.agriaura.viewmodel.AuthViewModel

data class GardenSpace(
    val name: String,
    val sunlight: String,
    val imageRes: Int,
    val iconRes: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateGardenScreen(navController: NavController) {
    val viewModel: AuthViewModel = viewModel()

    val gardenSpaces = listOf(
        GardenSpace("Terrace", "FULL SUN", R.drawable.terrace, R.drawable.ic_full_sun),
        GardenSpace("Balcony", "PARTIAL SUN", R.drawable.balcony, R.drawable.ic_partial),
        GardenSpace("Window Sill", "BRIGHT INDIRECT", R.drawable.window_sill, R.drawable.ic_bright_indirect),
        GardenSpace("Indoors", "LOW LIGHT", R.drawable.indoors, R.drawable.ic_low_light),
        GardenSpace("Rooftop", "DIRECT SUN", R.drawable.rooftop, R.drawable.ic_direct_sun)
    )

    var selectedSpace by remember { mutableStateOf<GardenSpace?>(gardenSpaces.first()) }

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
                    title = { Text("Create your Garden", color = Color.White) },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                Icons.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                )
            },
            bottomBar = {
                Button(
                    onClick = {
                        selectedSpace?.let { space ->
                            navController.navigate("plan_space/${space.name}")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF16C25D))
                ) {
                    Text("Continue", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
        ) { paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
            ) {

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Where do you want to grow plants?",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Text(
                    text = "Select the space closest to yours to get started.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.8f)
                )

                Spacer(modifier = Modifier.height(24.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(gardenSpaces) { space ->
                        GardenSpaceCard(
                            space = space,
                            isSelected = space == selectedSpace
                        ) {
                            selectedSpace = space
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GardenSpaceCard(
    space: GardenSpace,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val borderColor = if (isSelected) Color(0xFF16C25D) else Color.Transparent
    val backgroundColor = if (isSelected) Color(0xFFE3F8E9) else Color(0xFFF5F5F5)

    Card(
        modifier = Modifier
            .border(2.dp, borderColor, RoundedCornerShape(16.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Image(
                painter = painterResource(id = space.imageRes),
                contentDescription = space.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = space.name,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = space.iconRes),
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = space.sunlight,
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }

            if (isSelected) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    RadioButton(
                        selected = true,
                        onClick = null,
                        modifier = Modifier.align(Alignment.CenterEnd),
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color(0xFF16C25D)
                        )
                    )
                }
            }
        }
    }
}
