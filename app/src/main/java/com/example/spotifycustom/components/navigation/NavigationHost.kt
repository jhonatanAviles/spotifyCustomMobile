package com.example.spotifycustom.components.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.spotifycustom.navigation.NavScreen
import com.example.spotifycustom.screens.AlbumsScreen
import com.example.spotifycustom.screens.ArtistsScreen
import com.example.spotifycustom.screens.LoginScreen
import com.example.spotifycustom.screens.MyFavoriteSongsScreen
import com.example.spotifycustom.screens.ProfileScreen
import com.example.spotifycustom.screens.RegisterScreen
import com.example.spotifycustom.screens.SongsScreen
import com.example.spotifycustom.viewmodels.AuthViewModel
import com.example.spotifycustom.viewmodels.PaletteViewModel

@Composable
fun NavigationHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    paletteViewModel: PaletteViewModel,
    authViewModel: AuthViewModel,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = NavScreen.ScreenLogin.route
    ) {
        composable(
            route = NavScreen.ScreenArtists.route
        ) {
            // Replace with the Composable for the Artists screen
            ArtistsScreen(navController, paletteViewModel)
        }

        composable(
            route = NavScreen.ScreenAlbums.route + "?artistId={artistId}",
            arguments = listOf(
                navArgument("artistId") {
                    type = NavType.StringType
                    defaultValue = null
                    nullable = true
                }
            )
        ) { navBackStackEntry ->
            val artistId = navBackStackEntry.arguments?.getString("artistId")
            // Replace with the Composable for the Albums screen with the artistId
            AlbumsScreen(navController, artistId, paletteViewModel)
        }

        composable(
            route = NavScreen.ScreenSongs.route + "?albumId={albumId}",
            arguments = listOf(
                navArgument("albumId") {
                    type = NavType.StringType
                    defaultValue = null
                    nullable = true
                }
            )
        ) { navBackStackEntry ->
            val albumId = navBackStackEntry.arguments?.getString("albumId")
            // Replace with the Composable for the Songs screen with the albumId
            SongsScreen(albumId)
        }

        composable(
            route = NavScreen.ScreenFavoriteSongs.route,
        ) {
            MyFavoriteSongsScreen()
        }

        composable(
            route = NavScreen.ScreenLogin.route,
        ) {
            LoginScreen(navController, authViewModel)
        }

        composable(
            route = NavScreen.ScreenRegister.route,
        ) {
            RegisterScreen(navController, authViewModel)
        }

        composable(
            route = NavScreen.ScreenAccount.route,
        ) {
            ProfileScreen(navController, authViewModel)
        }
    }
}
