package com.priya.agriaura.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TopNavItemSelectable(
    label: String,
    active: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier

            .clickable { onClick() }
    ) {
        // pill background when active
        if (active) {
            Surface(
                shape = RoundedCornerShape(26.dp),
                color = Color(0x22FFFFFF), // faint white
                modifier = Modifier
                    .fillMaxWidth()
                    .height(34.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = label,
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(34.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = label,
                    color = Color.White,
                    fontSize = 14.sp
                )
            }
        }

        // small white underline only for active tab
        Box(
            modifier = Modifier
                .padding(top = 4.dp)
                .height(3.dp)
                .fillMaxWidth(0.35f)
                .background(
                    color = if (active) Color.White else Color.Transparent,
                    shape = RoundedCornerShape(50)
                )
        )
    }
}
