package com.example.spotifycustom.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.spotifycustom.components.navigation.BottomNavigation
import com.example.spotifycustom.components.navigation.NavigationHost
import com.example.spotifycustom.navigation.NavScreen
import com.example.spotifycustom.utils.GoogleAuthUiClient
import com.example.spotifycustom.viewmodels.AuthViewModel
import com.example.spotifycustom.viewmodels.PaletteViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.auth.api.identity.Identity

@Composable
fun MainView() {
    val navController = rememberNavController()

    val paletteViewModel = PaletteViewModel()

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = MaterialTheme.colors.background
    )

    val context = LocalContext.current
    val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = context,
            oneTapClient = Identity.getSignInClient(context)
        )
    }

    val authViewModel = viewModel<AuthViewModel>()

    LaunchedEffect(key1 = Unit) {
        if (googleAuthUiClient.getSignedInUser() != null) {
            authViewModel.isLoggedIn.value = true
        }
    }

    val isLoggedIn by authViewModel.isLoggedIn

    // Use Scaffold to create the basic layout with top app bar and bottom navigation
    Scaffold(
        bottomBar = {
            // Bottom Navigation
            if (isLoggedIn) BottomNavigation(NavScreen.values(), navController)
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        // Content of your screens goes here
        NavigationHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            paletteViewModel = paletteViewModel,
            authViewModel = authViewModel
        )
    }

}
