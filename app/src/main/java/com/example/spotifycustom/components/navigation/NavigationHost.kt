package com.example.spotifycustom.components.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.spotifycustom.navigation.NavScreen
import com.example.spotifycustom.screen.AlbumsScreen
import com.example.spotifycustom.screen.ArtistsScreen
import com.example.spotifycustom.screen.SongsScreen

@Composable
fun NavigationHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = NavScreen.ScreenArtists.route
    ) {
        composable(
            route = NavScreen.ScreenArtists.route
        ) {
            // Replace with the Composable for the Artists screen
            ArtistsScreen(navController)
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
            AlbumsScreen(navController, artistId)
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
            SongsScreen(navController, albumId)
        }
    }
}