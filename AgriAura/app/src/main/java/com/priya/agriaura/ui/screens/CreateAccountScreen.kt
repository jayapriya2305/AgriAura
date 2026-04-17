package com.priya.agriaura.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.priya.agriaura.R
import com.priya.agriaura.viewmodel.AuthViewModel
import com.priya.agriaura.network.RegisterRequest

@Composable
fun CreateAccountScreen(navController: NavHostController) {

    val viewModel: AuthViewModel = viewModel()
    var errorMsg by remember { mutableStateOf("") }

    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE9FBEA)) // 🌿 soft green background
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(14.dp))

        Image(
            painter = painterResource(id = R.drawable.agriaura_logo),
            contentDescription = "Logo",
            modifier = Modifier.size(72.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Create Account",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF0B4F2F)
        )

        Text(
            text = "Register to join AgriAura",
            fontSize = 14.sp,
            color = Color(0xFF4E7C5B)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(10.dp, RoundedCornerShape(24.dp)),
            color = Color(0xFFD8F3DA),
            shape = RoundedCornerShape(24.dp)
        ) {

            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                FieldBox(name, "Name") { name = it }
                FieldBox(username, "User name") { username = it }
                FieldBox(email, "Email") { email = it }
                FieldBox(phone, "Phone number") { phone = it }

                FieldBox(
                    text = password,
                    placeholder = "Password",
                    password = true,
                    visible = passwordVisible,
                    onToggleVisible = { passwordVisible = !passwordVisible }
                ) { password = it }

                FieldBox(
                    text = confirmPassword,
                    placeholder = "Confirm Password",
                    password = true,
                    visible = confirmPasswordVisible,
                    onToggleVisible = { confirmPasswordVisible = !confirmPasswordVisible }
                ) { confirmPassword = it }

                Spacer(modifier = Modifier.height(14.dp))

                if (errorMsg.isNotEmpty()) {
                    Text(
                        text = errorMsg,
                        color = Color.Red,
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {

                        if (
                            name.isBlank() ||
                            username.isBlank() ||
                            email.isBlank() ||
                            phone.isBlank() ||
                            password.isBlank() ||
                            confirmPassword.isBlank()
                        ) {
                            errorMsg = "Please fill all fields"
                            return@Button
                        }

                        val request = RegisterRequest(
                            name = name,
                            username = username,
                            email = email,
                            phone = phone,
                            password = password,
                            confirm_password = confirmPassword
                        )

                        viewModel.register(request) { status ->
                            when (status) {
                                "success" -> navController.navigate("login")
                                "email_exists" -> errorMsg = "Email already exists"
                                "password_mismatch" -> errorMsg = "Passwords do not match"
                                else -> errorMsg = "Registration failed"
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(30.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF0B6A33)
                    )
                ) {
                    Text(
                        text = "Register",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Text("Already have an account? ", color = Color.Gray)
            Text(
                "Login",
                color = Color(0xFF0B6A33),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    navController.navigate("login")
                }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun FieldBox(
    text: String,
    placeholder: String,
    password: Boolean = false,
    visible: Boolean = false,
    onToggleVisible: (() -> Unit)? = null,
    onValueChange: (String) -> Unit
) {
    val transform =
        if (password && !visible) PasswordVisualTransformation()
        else VisualTransformation.None

    OutlinedTextField(
        value = text,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                placeholder,
                fontSize = 15.sp,
                color = Color.Gray
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .shadow(6.dp, RoundedCornerShape(18.dp)),
        singleLine = true,
        shape = RoundedCornerShape(18.dp),
        textStyle = TextStyle(fontSize = 16.sp),
        visualTransformation = transform,
        trailingIcon = {
            if (password && onToggleVisible != null) {
                Text(
                    text = if (visible) "Hide" else "Show",
                    modifier = Modifier.clickable { onToggleVisible() },
                    color = Color(0xFF0B6A33),
                    fontSize = 14.sp
                )
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFFB7E4C7),
            unfocusedBorderColor = Color(0xFFD3EED8),
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            cursorColor = Color(0xFF0B6A33)
        )
    )
}
