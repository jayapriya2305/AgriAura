package com.priya.agriaura.ui.components


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun CaptureCard(
    icon: Int,
    text: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier   // ✅ REQUIRED
) {
    val border = if (isSelected)
        BorderStroke(2.dp, Color(0xFF16C25D))
    else
        BorderStroke(1.dp, Color.LightGray)

    Card(
        modifier = modifier.height(120.dp),
        shape = RoundedCornerShape(16.dp),
        border = border,
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.9f)
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                tint = Color(0xFF16C25D),
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text, fontWeight = FontWeight.Bold)
        }
    }
}
