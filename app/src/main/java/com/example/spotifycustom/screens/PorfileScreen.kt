package com.example.spotifycustom.screens

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.spotifycustom.components.profile.ProfileView
import com.example.spotifycustom.navigation.NavScreen
import com.example.spotifycustom.utils.GoogleAuthUiClient
import com.example.spotifycustom.viewmodels.AuthViewModel
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(navController: NavController, authViewModel: AuthViewModel) {

    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current

    val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = context,
            oneTapClient = Identity.getSignInClient(context)
        )
    }

    ProfileView(userData = googleAuthUiClient.getSignedInUser(),
        onSignOut = {
            coroutineScope.launch {
                authViewModel.isLoggedIn.value = false
                googleAuthUiClient.signOut()
                Toast.makeText(
                    context,
                    "Signed out",
                    Toast.LENGTH_LONG
                ).show()

                navController.navigate(NavScreen.ScreenLogin.route) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            }
        })
}