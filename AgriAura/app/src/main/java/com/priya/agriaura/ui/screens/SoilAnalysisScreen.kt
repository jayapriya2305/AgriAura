package com.priya.agriaura.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.priya.agriaura.R
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun SoilAnalysisScreen(
    navController: NavHostController,
    onGetRecommendation: ((phValue: Float, district: String, texture: String) -> Unit)? = null
) {
    val districts = remember { listOf("Select District", "Vellore", "Chennai", "Coimbatore", "Madurai", "Tiruchirappalli") }
    val textures = remember { listOf("Select Texture", "Sandy", "Loamy", "Clay", "Silty", "Peaty", "Chalky") }

    var phText by remember { mutableStateOf("") }
    var phError by remember { mutableStateOf<String?>(null) }
    var districtExpanded by remember { mutableStateOf(false) }
    var selectedDistrict by remember { mutableStateOf(districts[0]) }
    var textureExpanded by remember { mutableStateOf(false) }
    var selectedTexture by remember { mutableStateOf(textures[0]) }
    val focusManager = LocalFocusManager.current

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.soil_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color(0x66000000))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 18.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Soil pH Recommendation",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Select district, soil texture & input pH value to get suitable crops.",
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(18.dp))

            // pH input
            OutlinedTextField(
                value = phText,
                onValueChange = {
                    if (it.matches(Regex("^\\d{0,2}(\\.\\d{0,2})?$")) || it.isEmpty()) {
                        phText = it
                        phError = null
                    }
                },
                placeholder = { Text("Enter pH Value (0 - 14)") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White.copy(alpha = 0.95f),
                    unfocusedContainerColor = Color.White.copy(alpha = 0.95f),
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent,
                    cursorColor = Color.Black
                ),
                shape = RoundedCornerShape(12.dp)
            )
            if (phError != null) {
                Text(text = phError ?: "", color = Color.Yellow, modifier = Modifier.padding(top = 6.dp))
            }
            Spacer(modifier = Modifier.height(12.dp))

            // District dropdown
            Box(modifier = Modifier.fillMaxWidth()) {
                Card(
                    modifier = Modifier.fillMaxWidth().height(56.dp).clickable { districtExpanded = true },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.95f))
                ) {
                    Row(modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text(text = selectedDistrict, color = Color(0xFF1B1B1B), modifier = Modifier.weight(1f))
                        Icon(painter = painterResource(id = R.drawable.ic_dropdown), contentDescription = "open", tint = Color.Gray, modifier = Modifier.size(20.dp))
                    }
                }
                DropdownMenu(expanded = districtExpanded, onDismissRequest = { districtExpanded = false }, modifier = Modifier.fillMaxWidth()) {
                    districts.forEach { d ->
                        DropdownMenuItem(text = { Text(d) }, onClick = { selectedDistrict = d; districtExpanded = false })
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Texture dropdown
            Box(modifier = Modifier.fillMaxWidth()) {
                Card(
                    modifier = Modifier.fillMaxWidth().height(56.dp).clickable { textureExpanded = true },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.95f))
                ) {
                    Row(modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text(text = selectedTexture, color = Color(0xFF1B1B1B), modifier = Modifier.weight(1f))
                        Icon(painter = painterResource(id = R.drawable.ic_dropdown), contentDescription = "open", tint = Color.Gray, modifier = Modifier.size(20.dp))
                    }
                }
                DropdownMenu(expanded = textureExpanded, onDismissRequest = { textureExpanded = false }, modifier = Modifier.fillMaxWidth()) {
                    textures.forEach { t ->
                        DropdownMenuItem(text = { Text(t) }, onClick = { selectedTexture = t; textureExpanded = false })
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Button(
                onClick = {
                    focusManager.clearFocus()
                    val phValue = phText.toFloatOrNull()
                    when {
                        phText.isBlank() -> phError = "Enter pH value"
                        phValue == null -> phError = "Invalid pH"
                        phValue < 0f || phValue > 14f -> phError = "pH must be between 0 and 14"
                        selectedDistrict == districts[0] -> phError = "Please select district"
                        selectedTexture == textures[0] -> phError = "Please select texture"
                        else -> {
                            phError = null
                            onGetRecommendation?.invoke(phValue, selectedDistrict, selectedTexture)
                            val encodedDistrict = URLEncoder.encode(selectedDistrict, StandardCharsets.UTF_8.toString())
                            val encodedTexture = URLEncoder.encode(selectedTexture, StandardCharsets.UTF_8.toString())
                            navController.navigate("soil_recommendation/${phValue}/${encodedDistrict}/${encodedTexture}")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Text(text = "Get Recommendation", color = Color.Black, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SoilAnalysisScreenPreview() {
    val nav = rememberNavController()
    SoilAnalysisScreen(navController = nav, onGetRecommendation = { _, _, _ -> })
}
