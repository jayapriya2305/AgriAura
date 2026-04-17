package com.priya.agriaura.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.priya.agriaura.R

@Composable
fun TerraceIntroScreen(navController: NavController) {

    // Figma EXACT background: pure white
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF)), // pure white
        contentAlignment = Alignment.TopCenter
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // ---------- TITLE ----------
            Text(
                text = "Terrace\ngardening",
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(26.dp))

            // ---------- Figma EXACT CARD ----------
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(270.dp),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFBEFCC) // exact Figma peach-beige
                ),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.terrace_intro),
                    contentDescription = "Terrace Illustration",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentScale = ContentScale.Fit     // NO CROP
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            // ---------- SUBTITLE ----------
            Text(
                text = "Welcome to\nTerrace Gardening",
                textAlign = TextAlign.Center,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Manage your terrace garden and track your plants’ growth.",
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                color = Color(0xFF4A4A4A),
                modifier = Modifier.padding(horizontal = 10.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            // ---------- BUTTON ----------
            Button(
                onClick = {
                    navController.navigate("create_garden")
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF000000),   // Black background
                    contentColor = Color.White            // White text
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .height(52.dp)
            ) {
                Text(
                    text = "Get Started",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }


        }
        }
    }

