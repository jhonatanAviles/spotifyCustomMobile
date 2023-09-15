package com.example.spotifycustom.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

enum class NavScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    ScreenArtists("artists", "Artists", Icons.Filled.Person),
    ScreenAlbums("albums", "Albums", Icons.Filled.Album),
    ScreenSongs("songs", "Songs", Icons.Filled.MusicNote);

    companion object {
        fun from(route: String?) = when (route?.substringBefore("?")) {
            ScreenArtists.route -> ScreenArtists
            ScreenAlbums.route -> ScreenAlbums
            ScreenSongs.route -> ScreenSongs
            null -> ScreenArtists
            else -> throw IllegalArgumentException("Invalid route.")
        }
    }
}
