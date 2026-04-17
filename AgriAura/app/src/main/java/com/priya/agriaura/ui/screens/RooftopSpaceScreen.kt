package com.priya.agriaura.ui.screens

import androidx.compose.foundation.layout.Arrangement
import com.priya.agriaura.ui.components.DetectedZoneCard
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.priya.agriaura.R
import com.priya.agriaura.ui.components.CaptureCard
import com.priya.agriaura.ui.components.UnitButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RooftopSpaceScreen(navController: NavController) {

    var length by remember { mutableStateOf("12") }
    var width by remember { mutableStateOf("8") }
    val area = (length.toFloatOrNull() ?: 0f) * (width.toFloatOrNull() ?: 0f)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Define Dimensions") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        bottomBar = {
            Button(
                onClick = { navController.navigate("containerSelection/${area}") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF16C25D))
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Choose Containers", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.Default.ArrowForward, contentDescription = null)
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text("Plan your Rooftop", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Text("Input measurements to calculate the planting area.", color = Color.Gray)

            Spacer(modifier = Modifier.height(24.dp))

            // Dimensions
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Dimensions", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Row {
                    UnitButton(text = "FT", isSelected = true, onClick = {})
                    UnitButton(text = "M", isSelected = false, onClick = {})
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                DimensionTextField(
                    value = length,
                    onValueChange = { length = it },
                    label = "LENGTH",
                    modifier = Modifier.weight(1f),
                    unit = "ft"
                )
                DimensionTextField(
                    value = width,
                    onValueChange = { width = it },
                    label = "WIDTH",
                    modifier = Modifier.weight(1f),
                    unit = "ft"
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Total Area
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE9F8E8))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("TOTAL AREA", fontWeight = FontWeight.Bold, color = Color.Black, fontSize=12.sp)
                        Text("Auto-calculated", color = Color.Gray, fontSize=12.sp)
                    }
                    Text("${if (area > 0) "%.0f".format(area) else "0"} sq ft", fontWeight = FontWeight.Bold, fontSize = 24.sp, color = Color.Black)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Capture Space
            Text("Capture Space", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text("Upload or take photos to help us detect zones automatically.", color = Color.Gray)
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                CaptureCard(
                    icon = R.drawable.ic_camera,
                    text = "Use Camera",
                    isSelected = false,
                    modifier = Modifier.weight(1f)
                )
                CaptureCard(
                    icon = R.drawable.ic_add,
                    text = "Upload Photo",
                    isSelected = false,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Detected Zones
            Text("Detected Zones", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(16.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                item {
                    DetectedZoneCard(
                        icon = R.drawable.ic_season,
                        title = "Sunny Area",
                        subtitle = "South Facing"
                    )
                }
                item {
                    DetectedZoneCard(
                        icon = R.drawable.ic_add,
                        title = "High Wall",
                        subtitle = "Vertical Space"
                    )
                }
                item {
                    DetectedZoneCard(
                        icon = R.drawable.ic_add,
                        title = "Railing",
                        subtitle = "Hanging Pot"
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
