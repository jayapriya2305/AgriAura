package com.priya.agriaura.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.clickable
import com.priya.agriaura.R

@Composable
fun HomeScreen(navController: NavController) {

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.`home_bg`),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            // ---------- TOP ICONS ----------
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_calendar),
                        contentDescription = "Calendar",
                        modifier = Modifier.size(30.dp).clickable { navController.navigate("calendar") }
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_notification),
                        contentDescription = "Notifications",
                        modifier = Modifier.size(30.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Image(
                        painter = painterResource(id = R.drawable.icon_profile),
                        contentDescription = "Profile",
                        modifier = Modifier
                            .size(27.dp)
                            .clickable { navController.navigate("profile")                            }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ---------- SEARCH BAR ----------
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(30.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "Search",
                        modifier = Modifier.size(25.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Search plants, crops...",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ---------- WEATHER CARD ----------
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFD9F6FF)),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_weather),
                        contentDescription = "Weather",
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = "Weather in Vellore",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = Color(0xFF1D3B53)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Sunny · 30.7°C · 💧55%",
                            fontSize = 13.sp,
                            color = Color(0xFF1D3B53)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // ---------- FEATURE GRID ----------
            Column {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    FeatureCard(
                        title = "Terrace Gardening",
                        imageRes = R.drawable.terrace_gardening,
                        onClick = { navController.navigate("terrace_intro") }
                    )

                    FeatureCard(
                        title = "Disease Detection",
                        imageRes = R.drawable.disease_detection,
                        onClick = { navController.navigate("disease_detection") }
                    )

                }

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    FeatureCard(
                        title = "Crops",
                        imageRes = R.drawable.crops,
                        onClick = { navController.navigate("recommended_crops") }
                    )

                    FeatureCard(
                        title = "Soil Analysis",
                        imageRes = R.drawable.soil_analysis,
                        onClick = { navController.navigate("soil_analysis") }
                    )

                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // ---------- TIP CARD ----------
            TipCard(
                title = "Tip!",
                message = "Water early morning for better soil moisture."
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

/* ================== REUSABLE CARDS ================== */

// Feature card used for Terrace / Detection / Crops / Soil
@Composable
fun RowScope.FeatureCard(
    title: String,
    imageRes: Int,
    onClick: (() -> Unit)? = null
) {
    val cardModifier = if (onClick != null) {
        Modifier
            .weight(1f)
            .height(210.dp)
            .clickable { onClick() }
    } else {
        Modifier
            .weight(1f)
            .height(210.dp)
    }

    Card(
        modifier = cardModifier,
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF6FFF5)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun TipCard(
    title: String,
    message: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3C1)),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = title,
                color = Color(0xFFFF9800),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = message,
                color = Color.Black,
                fontSize = 14.sp
            )
        }
    }
}
