package com.example.spotifycustom.components.navigation

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.spotifycustom.navigation.NavScreen

@Composable
fun BottomNavigation(items: Array<NavScreen>, navController: NavHostController) {

    val backStackState = navController.currentBackStackEntryAsState()
    val currentScreen = NavScreen.from(backStackState.value?.destination?.route)

    val onScreenSelected = updateSelectedScreen(navController)

    Row {
        items.forEach { screen ->
            BottomNavigationItem(
                screen = screen,
                currentScreen = currentScreen,
                onScreenSelected = onScreenSelected
            )
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