package com.example.spotifycustom.components.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spotifycustom.navigation.NavScreen

@Composable
fun BottomNavigationItem(
    screen: NavScreen,
    currentScreen:  NavScreen,
    onScreenSelected: (NavScreen) -> Unit
) {
    val isSelected = currentScreen == screen

    val primaryColor = MaterialTheme.colors.primary

    Column(
        modifier = Modifier
            .background(if (isSelected) primaryColor.copy(alpha = 0.25f) else Color.Transparent)
            .clickable {
                if (!isSelected) {
                    onScreenSelected(screen)
                }
            }
            .clip(RoundedCornerShape(12))
            .padding(8.dp)
    ) {
        Icon(
            modifier = Modifier.size(32.dp),
            imageVector = screen.icon,
            contentDescription = screen.title,
            tint = if (isSelected) primaryColor else Color.Gray
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = screen.title,
            style = TextStyle(fontSize = 18.sp),
            color = if (isSelected) primaryColor else Color.Gray
        )
    }
}