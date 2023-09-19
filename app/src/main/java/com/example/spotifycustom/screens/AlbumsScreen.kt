package com.example.spotifycustom.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.spotifycustom.components.album.AlbumView
import com.example.spotifycustom.components.header.PageHeader
import com.example.spotifycustom.viewmodels.PaletteViewModel

@Composable
fun AlbumsScreen(
    navController: NavHostController,
    artistId: String?,
    paletteViewModel: PaletteViewModel
) {
    Column {
        PageHeader(title = if (artistId != null) "Albums" else "All Albums")
        AlbumView(
            navController = navController,
            artistId = artistId,
            paletteViewModel = paletteViewModel
        )
    }
}