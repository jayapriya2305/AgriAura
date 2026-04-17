package com.priya.agriaura.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.priya.agriaura.R

/* -----------------------------------------------------------
   1) HUB SCREEN – like first Figma image (4 big cards)
   ----------------------------------------------------------- */

@Composable
fun TipsHubScreen(navController: NavHostController) {

    Box(modifier = Modifier.fillMaxSize()) {

        // Background image
        Image(
            painter = painterResource(id = R.drawable.terrace_plants_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Dark overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xAA0A3A23))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {

            // Top bar
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Tips and learning hubs",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                // 1. Getting started
                HubTipCard(
                    imageRes = R.drawable.tips_getting_started,
                    title = "🌱 Getting started",
                    subtitle = "Learn the basics of starting a terrace garden"
                ) {
                    navController.navigate("tips_getting_started")
                }

                // 2. Container gardening
                HubTipCard(
                    imageRes = R.drawable.tips_container_gardening,
                    title = "🪴 Container gardening",
                    subtitle = "Tips on selecting and using containers"
                ) {
                    navController.navigate("tips_container_gardening")
                }

                // 3. Plant care
                HubTipCard(
                    imageRes = R.drawable.tips_plant_care,
                    title = "💧 Plant care",
                    subtitle = "Guides on watering and fertilizing"
                ) {
                    navController.navigate("tips_plant_care")
                }

                // 4. Pest & disease
                HubTipCard(
                    imageRes = R.drawable.tips_pest_disease,
                    title = "🐛 Pest & disease management",
                    subtitle = "Natural treatments and plant protection"
                ) {
                    navController.navigate("tips_pest_disease")
                }

                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

@Composable
private fun HubTipCard(
    imageRes: Int,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.85f)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Left image
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = title,
                modifier = Modifier
                    .width(90.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Text
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF1B1B1B)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = subtitle,
                    fontSize = 14.sp,
                    color = Color(0xFF424242)
                )
            }
        }
    }
}

/* -----------------------------------------------------------
   2) DETAIL SCREENS – 4 screens like your Figma
   ----------------------------------------------------------- */

@Composable
fun GettingStartedTipsScreen(navController: NavHostController) {
    BaseTipsDetailScreen(
        navController = navController,
        title = "🌱 Getting started",
        headerText = "Learn how to prepare your terrace for planting.",
        steps = listOf(
            StepCardData(
                heading = "🌱 Step 1: Clean and prepare your terrace",
                body = "Remove dust, debris, or moss. Ensure there's proper sunlight and drainage holes.\n\nAvoid water stagnation – standing water attracts pests and damages roots."
            ),
            StepCardData(
                heading = "🪴 Step 2: Choose suitable pots/containers",
                body = "Use terracotta or grow bags with good drainage. Small pots for herbs and medium pots for veggies.\n\nAvoid metal pots – they heat up fast in sun and can damage roots."
            )
        )
    )
}

@Composable
fun ContainerGardeningTipsScreen(navController: NavHostController) {
    BaseTipsDetailScreen(
        navController = navController,
        title = "🪴 Container gardening",
        headerText = "Container gardening makes it easy to grow fresh vegetables, herbs and flowers in limited spaces.",
        steps = listOf(
            StepCardData(
                heading = "🪴 Step 1: Choose the right container",
                body = "Pick containers with drainage holes to prevent waterlogging. Terracotta, plastic or fabric grow bags work best for terrace gardens.\n\nAvoid metal pots – they heat up quickly and stress the roots."
            ),
            StepCardData(
                heading = "🌱 Step 2: Select good soil mix",
                body = "Use a light mix: 40% garden soil, 40% compost and 20% cocopeat. This keeps the roots healthy and well-aerated.\n\nMix a handful of neem cake powder to keep soil pests away."
            ),
            StepCardData(
                heading = "☀️ Step 3: Placement & sunlight routine",
                body = "Most vegetables need at least 5–6 hours of direct sunlight.\n\nRotate pots weekly so plants grow straight.\nWater early morning or evening and add compost or liquid fertilizer every 15 days."
            )
        )
    )
}

@Composable
fun PlantCareTipsScreen(navController: NavHostController) {
    BaseTipsDetailScreen(
        navController = navController,
        title = "💧 Plant care",
        headerText = "Proper plant care keeps your terrace garden healthy and thriving.",
        steps = listOf(
            StepCardData(
                heading = "💧 Step 1: Watering schedule",
                body = "Water early in the morning or late evening. Avoid watering during midday heat as it evaporates quickly.\n\nUse a watering can with fine holes to avoid disturbing the soil.\nDo not overwater – soggy soil can cause root rot."
            ),
            StepCardData(
                heading = "🌱 Step 2: Fertilizing & composting",
                body = "Feed plants every 15 days with organic compost or vermicompost. Liquid fertilizers are great for quick nutrient boosts.\n\nMix compost evenly in the top soil layer.\nAvoid over-fertilizing, it can burn roots and slow growth."
            )
        )
    )
}

@Composable
fun PestDiseaseTipsScreen(navController: NavHostController) {
    BaseTipsDetailScreen(
        navController = navController,
        title = "🐛 Pest & disease management",
        headerText = "Keep your terrace plants healthy and pest-free using gentle, natural methods.",
        steps = listOf(
            StepCardData(
                heading = "🐛 Step 1: Identify common pests",
                body = "Look for holes, curled leaves, sticky residue or tiny insects on the undersides of leaves.\n\nCommon terrace garden pests: aphids, mealybugs, whiteflies, mites.\nInspect plants weekly, especially new leaves and buds."
            ),
            StepCardData(
                heading = "🌿 Step 2: Use organic remedies",
                body = "For sucking pests, spray neem oil (5 ml) mixed with 1 litre of water plus a few drops of mild soap.\n\nYellow spots or powdery patches often indicate fungal infection – improve air circulation and avoid overhead watering.\nMix baking soda (1 tsp) + water (1 L) and spray once a week on affected leaves."
            )
        )
    )
}

/* ---------- Shared layout for detail screens ---------- */

data class StepCardData(
    val heading: String,
    val body: String
)

@Composable
private fun BaseTipsDetailScreen(
    navController: NavHostController,
    title: String,
    headerText: String,
    steps: List<StepCardData>
) {

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.terrace_plants_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xAA0A3A23))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .verticalScroll(rememberScrollState())
        ) {

            // Top bar
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Main intro card
            Surface(
                shape = RoundedCornerShape(18.dp),
                color = Color.White.copy(alpha = 0.90f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = headerText,
                    fontSize = 15.sp,
                    color = Color(0xFF1B1B1B),
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Steps
            steps.forEach { step ->
                Surface(
                    shape = RoundedCornerShape(18.dp),
                    color = Color.White.copy(alpha = 0.92f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = step.heading,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF1B1B1B)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = step.body,
                            fontSize = 14.sp,
                            color = Color(0xFF424242)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}
