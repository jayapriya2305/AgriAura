package com.priya.agriaura.ui.components


import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun UnitButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier   // ✅ THIS FIXES "modifier not found"
) {
    Button(
        onClick = onClick,
        modifier = modifier.size(40.dp),
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color(0xFFE9F8E8) else Color.White,
            contentColor = if (isSelected) Color(0xFF16C25D) else Color.Black
        )
    ) {
        Text(text, fontWeight = FontWeight.Bold)
    }
}
