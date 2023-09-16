package com.example.spotifycustom.components.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.spotifycustom.navigation.NavScreen

@Composable
fun BottomNavigation(items: Array<NavScreen>, navController: NavHostController) {

    val backStackState = navController.currentBackStackEntryAsState()
    val currentScreen = NavScreen.from(backStackState.value?.destination?.route)

    val onScreenSelected = updateSelectedScreen(navController)

    // Define a custom shape with rounded corners at the top
    val shape =
        RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp, bottomEnd = 0.dp, bottomStart = 0.dp)

    // Use Modifier.elevation to add a slight elevation
    Box(
        modifier = Modifier
            .background(MaterialTheme.colors.surface)
            .fillMaxWidth()
            .shadow(1.dp, shape),
    ) {
        Row(
            modifier = Modifier
                .clip(shape)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly // Centers items horizontally with equal spacing
        ) {
            items.forEach { screen ->
                if (screen != NavScreen.ScreenLogin && screen != NavScreen.ScreenRegister)
                    BottomNavigationItem(
                        screen = screen,
                        currentScreen = currentScreen,
                        onScreenSelected = onScreenSelected
                    )
            }
        }
    }
}

fun updateSelectedScreen(
    navController: NavController
): (NavScreen) -> Unit {
    return { item ->
        navController.navigate(item.route) {

            launchSingleTop = true
        }
    }
}