package com.priya.agriaura.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.priya.agriaura.R
import com.priya.agriaura.viewmodel.AuthViewModel
import com.priya.agriaura.network.LoginRequest

@Composable
fun LoginScreen(navController: NavHostController) {

    // ✅ ViewModel
    val viewModel: AuthViewModel = viewModel()

    // ✅ Error message
    var errorMsg by remember { mutableStateOf("") }

    // ✅ Form fields
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFFBFF7C9),
                        Color(0xFFBFF7C9)
                    )
                )
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            Spacer(modifier = Modifier.height(60.dp))

            Image(
                painter = painterResource(id = R.drawable.agriaura_logo),
                contentDescription = "AgriAura Logo",
                modifier = Modifier.size(120.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Welcome Back 👋",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Login to continue to AgriAura",
                fontSize = 16.sp,
                color = Color(0xFF4E342E),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(30.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("Email", color = Color.Gray) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFF2F2F2),
                    focusedContainerColor = Color(0xFFF2F2F2),
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("Password", color = Color.Gray) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFF2F2F2),
                    focusedContainerColor = Color(0xFFF2F2F2),
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.Black
                )
            )

            Box(modifier = Modifier.fillMaxWidth()) {
                TextButton(
                    onClick = {
                        navController.navigate("forgot_password")
                    },
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Text("Forgot password?", color = Color.Black)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ✅ ERROR MESSAGE (CORRECT POSITION)
            if (errorMsg.isNotEmpty()) {
                Text(
                    text = errorMsg,
                    color = Color.Red,
                    fontSize = 14.sp
                )
            }

            Button(
                onClick = {

                    // ✅ BASIC VALIDATION
                    if (email.isBlank() || password.isBlank()) {
                        errorMsg = "Please enter email and password"
                        return@Button
                    }

                    val request = LoginRequest(
                        email = email,
                        password = password
                    )

                    viewModel.login(request) { response ->
                        when (response?.status) {
                            "success" -> navController.navigate("home")
                            "invalid" -> errorMsg = "Invalid email or password"
                            "network_error" -> errorMsg = "Network error"
                            else -> errorMsg = "Login failed"
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF004D40)
                )
            ) {
                Text(
                    text = "Log in",
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(22.dp))

            TextButton(
                onClick = {
                    navController.navigate("create_account")
                }
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Black)) {
                            append("Don't have an Account? ")
                        }
                        withStyle(style = SpanStyle(color = Color.Red)) {
                            append("Create Account")
                        }
                    },
                    fontSize = 16.sp
                )
            }
        }
    }
}
