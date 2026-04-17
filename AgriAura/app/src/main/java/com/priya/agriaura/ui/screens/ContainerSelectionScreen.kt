package com.priya.agriaura.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.priya.agriaura.R
import com.priya.agriaura.network.ContainerRequest
import com.priya.agriaura.viewmodel.AuthViewModel





data class Container(
    val id: Int,
    val name: String,
    val image: Int,
    val features: List<Pair<String, ImageVector>>,
    val area: Int,
    val type: String,
    val isTopMatch: Boolean = false
)

val containers = listOf(
    Container(1, "5-Tier Vertical Tower", R.drawable.vertical_tower, listOf("Excellent Drainage" to Icons.Default.Opacity, "2 sq ft used" to Icons.Default.SquareFoot), 2, "Vertical", isTopMatch = true),
    Container(2, "Breathable Fabric Bed", R.drawable.breathable_fabric_bed, listOf("Air Pruning" to Icons.Default.Air, "4 sq ft used" to Icons.Default.SquareFoot), 4, "Fabric"),
    Container(3, "Clay Pot Trio", R.drawable.clay_pot_trio, listOf("Temp Control" to Icons.Default.Thermostat, "3 sq ft used" to Icons.Default.SquareFoot), 3, "Clay"),
    Container(4, "Cedar Raised Bed", R.drawable.cedar_raised_bed, listOf("Pest Resistant" to Icons.Default.BugReport, "6 sq ft used" to Icons.Default.SquareFoot), 6, "Wood"),
    Container(5, "Rail Hanging Pot", R.drawable.rail_handling_pot, listOf("Space Saver" to Icons.Default.AspectRatio, "1 sq ft used" to Icons.Default.SquareFoot), 1, "Vertical", isTopMatch = true),
    Container(6, "Mod Metal Box", R.drawable.mod_metal_box, listOf("Heat Retaining" to Icons.Default.Whatshot, "3 sq ft used" to Icons.Default.SquareFoot), 3, "Metal")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContainerSelectionScreen(navController: NavController, area: Float, planSpace: String) {


    val viewModel: AuthViewModel = viewModel()

    var selectedContainers by remember { mutableStateOf<Set<Int>>(emptySet()) }
    val filters = listOf("All", "Fabric", "Clay", "Vertical", "Wood", "Metal")
    var selectedFilter by remember { mutableStateOf("All") }

    val filteredContainers = if (selectedFilter == "All") {
        containers
    } else {
        containers.filter { it.type == selectedFilter }
    }

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
                    title = { Text("Best for your ${area.toInt()} sq ft space", color = Color.White) },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                )
            },
            bottomBar = {
                Button(
                    onClick = {

                        val selectedTypes = selectedContainers
                            .mapNotNull { id -> containers.find { it.id == id }?.type }
                            .distinct()
                            .joinToString(",")

                        val request = ContainerRequest(
                            user_id = 1, // later replace with logged-in user id
                            plan_space = planSpace,
                            area = area,
                            container_types = selectedTypes
                        )

                        viewModel.saveContainers(request) { status ->
                            if (status == "success") {
                                navController.navigate("what_to_grow/$planSpace/$selectedTypes")
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF16C25D)),
                    enabled = selectedContainers.isNotEmpty()
                ) {
                    Text(
                        text = "Choose ${selectedContainers.size} Containers",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
                }
            }


        ) { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filters) { filter ->
                        FilterChip(
                            selected = filter == selectedFilter,
                            onClick = { selectedFilter = filter },
                            label = { Text(filter) },
                            colors = FilterChipDefaults.filterChipColors(
                                containerColor = Color.White.copy(alpha = 0.9f),
                                labelColor = Color.Black,
                                selectedContainerColor = Color(0xFF16C25D),
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(filteredContainers) { container ->
                        ContainerCard(
                            container = container,
                            isSelected = selectedContainers.contains(container.id),
                            onSelect = {
                                selectedContainers = if (selectedContainers.contains(container.id)) {
                                    selectedContainers - container.id
                                } else {
                                    selectedContainers + container.id
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContainerCard(container: Container, isSelected: Boolean, onSelect: () -> Unit) {
    Card(
        modifier = Modifier.clickable(onClick = onSelect),
        shape = RoundedCornerShape(16.dp),
        border = if (isSelected) BorderStroke(2.dp, Color(0xFF16C25D)) else null,
        colors = CardDefaults.cardColors(containerColor = if(isSelected) Color(0xFFE9F8E8).copy(alpha = 0.9f) else Color.White.copy(alpha = 0.9f))
    ) {
        Column {
            Box(modifier = Modifier.height(120.dp)) {
                Image(
                    painter = painterResource(id = container.image),
                    contentDescription = container.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                if (container.isTopMatch) {
                    Surface(
                        modifier = Modifier.padding(8.dp).align(Alignment.TopStart),
                        shape = RoundedCornerShape(4.dp),
                        color = Color(0xFF16C25D)
                    ) {
                        Text(
                            text = "TOP MATCH",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
                if (isSelected) {
                    Box(modifier = Modifier.align(Alignment.TopEnd).padding(8.dp).size(24.dp).clip(CircleShape).background(Color(0xFF16C25D)),
                        contentAlignment = Alignment.Center
                        ) {
                        Icon(Icons.Default.Check, contentDescription = "Selected", tint = Color.White, modifier = Modifier.size(16.dp))
                    }
                }
            }
            Column(modifier = Modifier.padding(16.dp)) {
                Text(container.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(8.dp))
                container.features.forEach { feature ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(feature.second, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(feature.first, color = Color.Gray, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}
