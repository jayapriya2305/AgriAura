package com.priya.agriaura.ui.screens

import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.priya.agriaura.utils.uriToBitmap
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiseaseDetectionScreen(navController: NavController) {

    val context = LocalContext.current

    // 🧠 Image state
    var selectedBitmap by remember { mutableStateOf<Bitmap?>(null) }

    // UI state
    var prediction by remember { mutableStateOf("No prediction yet") }
    var symptoms by remember { mutableStateOf("No symptoms yet") }
    var treatment by remember { mutableStateOf("No treatment yet") }
    var prevention by remember { mutableStateOf("No prevention yet") }

    // 📷 Image Picker Launcher
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            selectedBitmap = uriToBitmap(context, it)
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Disease Detector",
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // 🖼 IMAGE PREVIEW
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .background(Color.LightGray, RoundedCornerShape(16.dp))
                    .clickable {
                        imagePickerLauncher.launch("image/*")
                    },
                contentAlignment = Alignment.Center
            ) {
                if (selectedBitmap != null) {
                    Image(
                        bitmap = selectedBitmap!!.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Default.Image,
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Tap to select image", color = Color.Gray)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 🔘 BUTTONS
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Button(
                    onClick = {
                        imagePickerLauncher.launch("image/*")
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3))
                ) {
                    Text("Choose Image")
                }

                Spacer(modifier = Modifier.width(12.dp))

                Button(
                    onClick = {
                        if (selectedBitmap == null) {
                            prediction = "Please select an image first"
                            return@Button
                        }

                        // TEMP DEMO RESULT (replace with model later)
                        prediction = "Apple___Cedar_apple_rust"
                        symptoms = "Orange spots on leaves and fruit"
                        treatment = "Apply fungicide, remove infected leaves"
                        prevention = "Plant resistant varieties, avoid cedar trees"
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                ) {
                    Text("Detect Disease")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 📊 RESULTS
            ResultCard("Prediction", prediction, Color(0xFF4CAF50))
            ResultCard("Symptoms", symptoms, Color(0xFFFF9800))
            ResultCard("Treatment", treatment, Color(0xFFF44336))
            ResultCard("Prevention", prevention, Color(0xFF2196F3))
        }
    }
}

@Composable
fun ResultCard(title: String, value: String, color: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                color = color,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = value, fontSize = 15.sp)
        }
    }
}
