package com.example.spotifycustom.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.spotifycustom.components.register.RegisterView
import com.example.spotifycustom.viewmodels.AuthViewModel

@Composable
fun RegisterScreen(navController: NavController, authViewModel: AuthViewModel) {
    RegisterView(navController = navController, authViewModel = authViewModel)
}