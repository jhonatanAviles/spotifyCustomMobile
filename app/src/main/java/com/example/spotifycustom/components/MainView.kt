package com.example.spotifycustom.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.spotifycustom.components.navigation.BottomNavigation
import com.example.spotifycustom.components.navigation.NavigationHost
import com.example.spotifycustom.navigation.NavScreen
import com.example.spotifycustom.viewmodels.PaletteViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun MainView() {
    val navController = rememberNavController()

    val paletteViewModel = PaletteViewModel()

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = MaterialTheme.colors.background
    )

    // Use Scaffold to create the basic layout with top app bar and bottom navigation
    Scaffold(
        bottomBar = {
            // Bottom Navigation
            BottomNavigation(NavScreen.values(), navController)
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        // Content of your screens goes here
        NavigationHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            paletteViewModel = paletteViewModel,
        )
    }

}
