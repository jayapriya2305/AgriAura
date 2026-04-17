package com.priya.agriaura.ui.screens

import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.priya.agriaura.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LayoutRecommendationScreen(navController: NavHostController) {

    // ---------- STATE ----------
    var detectedAreaSqFt by remember { mutableIntStateOf(35) }
    var recommendedPots by remember { mutableIntStateOf(23) }
    var recommendedBeds by remember { mutableIntStateOf(4) }

    var selectedSunlight by remember { mutableIntStateOf(0) }   // 0 Full, 1 Partial, 2 Low
    var selectedPlantType by remember { mutableIntStateOf(0) }  // 0 Veg, 1 Herbs, 2 Flowers

    var terraceImageUri by remember { mutableStateOf<Uri?>(null) }
    var terraceCameraBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var showSourceDialog by remember { mutableStateOf(false) }

    // dialog for length / width
    var showAreaDialog by remember { mutableStateOf(false) }
    var lengthText by remember { mutableStateOf("") }
    var widthText by remember { mutableStateOf("") }


    // ---------- IMAGE PICKERS ----------
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            terraceImageUri = uri
            terraceCameraBitmap = null
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        if (bitmap != null) {
            terraceCameraBitmap = bitmap
            terraceImageUri = null
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Layout") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        containerColor = Color(0xFF01411C)
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            Image(
                painter = painterResource(id = R.drawable.terrace_plants_bg),
                contentDescription = "Terrace plants background",
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.9f),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                // -------- TERRACE PHOTO CARD --------
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .clip(RoundedCornerShape(20.dp))
                ) {
                    AsyncImage(
                        model = terraceCameraBitmap ?: terraceImageUri ?: R.drawable.terrace_placeholder,
                        contentDescription = "Terrace photo",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    // camera circle (tap → choose gallery or camera)
                    Surface(
                        modifier = Modifier
                            .size(80.dp)
                            .align(Alignment.Center)
                            .clickable { showSourceDialog = true },
                        color = Color.White.copy(alpha = 0.96f),
                        shape = CircleShape,
                        shadowElevation = 6.dp
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_camera),
                                contentDescription = "Upload terrace photo",
                                modifier = Modifier.size(34.dp)
                            )
                        }
                    }

                    // bottom dark strip so text is visible
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .fillMaxWidth()
                            .background(Color(0xAA000000)) // semi-transparent black
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "Terrace area detected: ${detectedAreaSqFt} sq ft",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                        Text(
                            text = "Recommended: $recommendedPots pots + $recommendedBeds beds",
                            fontSize = 11.sp,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    }

                    // edit icon bottom-right  → opens length/width dialog
                    Surface(
                        modifier = Modifier
                            .size(46.dp)
                            .align(Alignment.BottomEnd)
                            .padding(10.dp)
                            .clickable { showAreaDialog = true },
                        shape = CircleShape,
                        color = Color.White,
                        shadowElevation = 4.dp
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_edit),
                                contentDescription = "Edit area",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // -------- SUNLIGHT CHIPS --------
                Text(
                    text = "Sunlight",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SunlightChip("Full Sun", selectedSunlight == 0) { selectedSunlight = 0 }
                    SunlightChip("Partial", selectedSunlight == 1) { selectedSunlight = 1 }
                    SunlightChip("Low Light", selectedSunlight == 2) { selectedSunlight = 2 }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // -------- PLANTS TO GROW CHIPS --------
                Text(
                    text = "Plants To Grow",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    PlantChip("Vegetables", selectedPlantType == 0) { selectedPlantType = 0 }
                    PlantChip("Herbs", selectedPlantType == 1) { selectedPlantType = 1 }
                    PlantChip("Flowers", selectedPlantType == 2) { selectedPlantType = 2 }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // -------- RE-ANALYZE + ADD BUTTON --------
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Re-analyze button
                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp),
                        onClick = {
                            // TODO: put your re-analyze logic here later
                        },
                        shape = RoundedCornerShape(26.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFE5FBEA),
                            contentColor = Color(0xFF008C4A)
                        )
                    ) {
                        Text(
                            text = "Re-analyze Layout",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    // + Add Plant button (SEPARATE composable, not inside onClick)
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE5FBEA))
                            .clickable { navController.navigate("add_plant") },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_add),
                            contentDescription = "Add plant",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }

            }
        }
    }


    // -------- GALLERY / CAMERA DIALOG --------
    if (showSourceDialog) {
        AlertDialog(
            onDismissRequest = { showSourceDialog = false },
            title = { Text("Upload terrace photo") },
            text = { Text("Choose photo source") },
            confirmButton = {
                TextButton(onClick = {
                    showSourceDialog = false
                    galleryLauncher.launch("image/*")
                }) {
                    Text("Gallery")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showSourceDialog = false
                    cameraLauncher.launch(null)
                }) {
                    Text("Camera")
                }
            }
        )
    }

    // -------- LENGTH / WIDTH DIALOG --------
    if (showAreaDialog) {
        AlertDialog(
            onDismissRequest = { showAreaDialog = false },
            title = { Text("Enter terrace size") },
            text = {
                Column {
                    Text(
                        "Give approximate terrace length and width in feet.",
                        fontSize = 13.sp
                    )
                    Spacer(Modifier.height(8.dp))

                    OutlinedTextField(
                        value = lengthText,
                        onValueChange = { lengthText = it },
                        label = { Text("Length (ft)") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(8.dp))

                    OutlinedTextField(
                        value = widthText,
                        onValueChange = { widthText = it },
                        label = { Text("Width (ft)") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    // calculate area and recommendations
                    val length = lengthText.toFloatOrNull() ?: 0f
                    val width = widthText.toFloatOrNull() ?: 0f
                    val area = (length * width).toInt().coerceAtLeast(1)

                    detectedAreaSqFt = area

                    // very simple rules – you can tweak later
                    recommendedPots = (area / 1.5f).toInt().coerceAtLeast(1)
                    recommendedBeds = (area / 8f).toInt().coerceAtLeast(1)

                    showAreaDialog = false
                }) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAreaDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}


/* ---------- CHIPS ---------- */

@Composable
fun SunlightChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val background = if (selected) Color.White else Color(0xFFF5F5F5)
    val content = if (selected) Color(0xFF0B8D4D) else Color.Black

    Surface(
        modifier = Modifier
            .height(40.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(22.dp),
        color = background
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 18.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = label,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = content
            )
        }
    }
}

@Composable
fun PlantChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val background = if (selected) Color.White else Color(0xFFF5F5F5)
    val content = if (selected) Color(0xFF0B8D4D) else Color.Black

    Surface(
        modifier = Modifier
            .height(40.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(22.dp),
        color = background
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 18.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = label,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = content
            )
        }
    }
}
