package com.priya.agriaura.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.priya.agriaura.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    // simple fade-in animation
    var visible by remember { mutableStateOf(false) }
    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis = 700)
    )

    LaunchedEffect(Unit) {
        visible = true
        delay(2000) // show splash for 2 seconds
        navController.navigate("login") {
            popUpTo("splash") { inclusive = true }
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        // single full-image splash (logo included inside the PNG)
        Image(
            painter = painterResource(id = R.drawable.agriaura_splash),
            contentDescription = "Splash",
            modifier = Modifier
                .fillMaxSize()
                .alpha(alpha),
            contentScale = ContentScale.Crop
        )
    }
}
