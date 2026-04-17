package com.priya.agriaura.ui.screens

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.priya.agriaura.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanSpaceScreen(
    navController: NavController,
    spaceType: String
) {
    val context = LocalContext.current

    var length by remember { mutableStateOf("12") }
    var width by remember { mutableStateOf("8") }
    var errorMsg by remember { mutableStateOf("") }


    val area = (length.toFloatOrNull() ?: 0f) * (width.toFloatOrNull() ?: 0f)

    // ---------- CAMERA + GALLERY STATE ----------
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    fun createImageUri(context: Context): Uri {
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        }
        return context.contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            values
        )!!
    }

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { }

    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            imageUri = uri
        }

    // ---------- UI ----------
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
                    title = { Text("Define Dimensions", color = Color.White) },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Default.ArrowBack, null, tint = Color.White)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                )
            },
            bottomBar = {
                Button(
                    onClick = {
                        val lengthValue = length.toIntOrNull()
                        val widthValue = width.toIntOrNull()

                        if (lengthValue == null || widthValue == null) {
                            errorMsg = "Enter valid numbers"
                            return@Button
                        }

                        val finalArea = (lengthValue * widthValue).toFloat()

                        navController.navigate("containerSelection/$finalArea/$spaceType")

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF16C25D))
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Choose Containers", fontWeight = FontWeight.Bold)
                        Spacer(Modifier.width(8.dp))
                        Icon(Icons.Default.ArrowForward, null)
                    }
                }
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState())
            ) {

                Text("Plan your space", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Text("Input measurements to calculate the planting area.", color = Color.White.copy(0.8f))

                Spacer(Modifier.height(24.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    DimensionTextField(length, { length = it }, "LENGTH", "ft", Modifier.weight(1f))
                    DimensionTextField(width, { width = it }, "WIDTH", "ft", Modifier.weight(1f))
                }

                Spacer(Modifier.height(24.dp))

                // ---------- CAPTURE SPACE ----------
                Text("Capture Space", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color.White)
                Text(
                    "Upload or take photos to help us detect zones automatically.",
                    color = Color.White.copy(0.8f)
                )

                Spacer(Modifier.height(16.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {

                    // USE CAMERA
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                val uri = createImageUri(context)
                                imageUri = uri
                                cameraLauncher.launch(uri)
                            }
                    ) {
                        CaptureCard(
                            icon = R.drawable.ic_camera,
                            text = "Use Camera",
                            isSelected = false
                        )
                    }

                    // UPLOAD PHOTO
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                galleryLauncher.launch("image/*")
                            }
                    ) {
                        CaptureCard(
                            icon = R.drawable.ic_add,
                            text = "Upload Photo",
                            isSelected = false
                        )
                    }
                }

                Spacer(Modifier.height(24.dp))

                // ---------- DETECTED ZONES ----------
                Text("Detected Zones", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color.White)

                Spacer(Modifier.height(16.dp))

                LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    item { DetectedZoneCard(R.drawable.ic_season, "Sunny Area", "South Facing") }
                    item { DetectedZoneCard(R.drawable.ic_add, "High Wall", "Vertical Space") }
                    item { DetectedZoneCard(R.drawable.ic_add, "Railing", "Hanging Pot") }
                }

                Spacer(Modifier.height(32.dp))
            }
        }
    }
}

/* ---------- EXISTING UI COMPOSABLES (UNCHANGED) ---------- */

@Composable
fun CaptureCard(icon: Int, text: String, isSelected: Boolean) {
    val border =
        if (isSelected) BorderStroke(2.dp, Color(0xFF16C25D))
        else BorderStroke(1.dp, Color.LightGray)

    Card(
        modifier = Modifier.height(120.dp),
        shape = RoundedCornerShape(16.dp),
        border = border,
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(painterResource(icon), null, tint = Color(0xFF16C25D), modifier = Modifier.size(32.dp))
            Spacer(Modifier.height(8.dp))
            Text(text, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun DimensionTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    unit: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, fontSize = 12.sp) },
        trailingIcon = { Text(unit, color = Color.Gray) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = modifier
            .height(60.dp)
            .background(Color.White, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFF16C25D),
            unfocusedBorderColor = Color.Transparent,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            cursorColor = Color(0xFF16C25D),
            focusedLabelColor = Color.Gray,
            unfocusedLabelColor = Color.Gray
        )
    )
}


@Composable
fun DetectedZoneCard(icon: Int, title: String, subtitle: String) {
    Card(
        modifier = Modifier.size(120.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Icon(painterResource(icon), null, modifier = Modifier.size(24.dp))
            Spacer(Modifier.height(16.dp))
            Text(title, fontWeight = FontWeight.Bold)
            Text(subtitle, color = Color.Gray)
        }
    }
}
