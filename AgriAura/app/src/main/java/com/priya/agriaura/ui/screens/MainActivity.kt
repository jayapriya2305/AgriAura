package com.priya.agriaura.ui.screens

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.priya.agriaura.AddPlantScreen
import com.priya.agriaura.MyGardenDashboardScreen
import com.priya.agriaura.SoilRecommendationScreen
import com.priya.agriaura.theme.terrace.PlantRecommendationScreen


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AgriAuraTheme {
                AgriAuraApp()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun AgriAuraApp() {
    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {

        // Splash
        composable("splash") {
            SplashScreen(navController)
        }

        // Login + Create account
        composable("login") {
            LoginScreen(navController)
        }

        composable("create_account") {
            CreateAccountScreen(navController)
        }

        composable("forgot_password") {
            ForgotPasswordScreen(navController)
        }

        composable("new_credentials") {
            NewCredentialsScreen(navController)
        }

        composable("password_updated") {
            PasswordUpdatedScreen(navController)
        }

        // Home
        composable("home") {
            HomeScreen(navController)
        }

        // Calendar
        composable("calendar") {
            CalendarScreen(navController)
        }


        composable("profile_details") {
            ProfileDetailsScreen(navController)
        }

        composable("delete_account") {
            DeleteAccountScreen(navController)
        }

        // Soil Analysis
        composable("soil_analysis") {
            SoilAnalysisScreen(navController)
        }
        composable("disease_detection") {
            DiseaseDetectionScreen(navController)
        }


        composable(
            route = "soil_recommendation/{phValue}/{district}/{texture}",
            arguments = listOf(
                navArgument("phValue") { type = NavType.FloatType },
                navArgument("district") { type = NavType.StringType },
                navArgument("texture") { type = NavType.StringType }
            )
        ) { entry ->
            val phValue = entry.arguments?.getFloat("phValue") ?: 0f
            val district = entry.arguments?.getString("district") ?: ""
            val texture = entry.arguments?.getString("texture") ?: ""
            SoilRecommendationScreen(navController, phValue, district, texture)
        }


        // Terrace Gardening
        composable("terrace_intro") {
            TerraceIntroScreen(navController)
        }
        composable("create_garden") {
            CreateGardenScreen(navController)
        }
        composable("plan_space/{spaceType}") { backStackEntry ->
            val spaceType = backStackEntry.arguments?.getString("spaceType") ?: ""
            PlanSpaceScreen(
                navController = navController,
                spaceType = spaceType
            )
        }

        composable("balcony_space") {
            BalconySpaceScreen(navController)
        }
        composable("windows_space") {
            WindowsSpaceScreen(navController)
        }
        composable("rooftop_space") {
            RooftopSpaceScreen(navController)
        }
        composable("indoors_space") {
            IndoorsSpaceScreen(navController)
        }
        composable(
            route = "containerSelection/{area}/{spaceType}"
        ) { backStackEntry ->

            val area = backStackEntry.arguments
                ?.getString("area")
                ?.toFloatOrNull() ?: 0f

            val spaceType = backStackEntry.arguments
                ?.getString("spaceType") ?: ""

            ContainerSelectionScreen(
                navController = navController,
                area = area,
                planSpace = spaceType
            )
        }

        composable(
            route = "what_to_grow/{planSpace}/{selectedContainerTypes}",
            arguments = listOf(
                navArgument("planSpace") { type = NavType.StringType },
                navArgument("selectedContainerTypes") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val planSpace = backStackEntry.arguments?.getString("planSpace") ?: ""
            val selectedContainerTypes =
                backStackEntry.arguments
                    ?.getString("selectedContainerTypes")
                    ?.split(",") ?: emptyList()
            WhatToGrowScreen(navController, planSpace, selectedContainerTypes)
        }
        composable(
            route = "plant_recommendations/{space}/{category}/{container}"
        ) { backStackEntry ->

            val space = backStackEntry.arguments?.getString("space") ?: ""
            val category = backStackEntry.arguments?.getString("category") ?: ""
            val container = backStackEntry.arguments?.getString("container") ?: ""

            PlantRecommendationScreen(
                navController = navController,
                selectedSpace = space,
                selectedCategory = category,
                selectedContainer = container
            )
        }



        // Add Plant
        composable(
            route = "add_plant/{plantName}/{selectedArea}",
            arguments = listOf(
                navArgument("plantName") { type = NavType.StringType },
                navArgument("selectedArea") { type = NavType.StringType },
            )
        ) { backStackEntry ->
            val plantName = backStackEntry.arguments?.getString("plantName") ?: ""
            val selectedArea = backStackEntry.arguments?.getString("selectedArea") ?: ""
            AddPlantScreen(
                navController = navController,
                plantName = plantName,
                selectedArea = selectedArea
            )
        }

        composable("my_garden") {
            MyGardenDashboardScreen(navController)
        }

        // Recommended Crops list
        composable("recommended_crops") {
            RecommendedCropsScreen(navController)
        }

        // Crop Detail Screen
        composable(
            route = "crop_detail/{cropName}/{imageRes}",
            arguments = listOf(
                navArgument("cropName") { type = NavType.StringType },
                navArgument("imageRes") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val cropName = backStackEntry.arguments?.getString("cropName")
            val imageRes = backStackEntry.arguments?.getInt("imageRes") ?: 0
            CropDetailScreen(
                navController = navController,
                cropName = cropName,
                imageRes = imageRes
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview(showBackground = true)
@Composable
fun AgriAuraPreview() {
    AgriAuraTheme {
        AgriAuraApp()
    }
}
