package com.example.spotifycustom.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.spotifycustom.components.artist.ArtistView
import com.example.spotifycustom.components.header.PageHeader
import com.example.spotifycustom.viewmodels.PaletteViewModel

@Composable
fun ArtistsScreen(
    navController: NavHostController,
    paletteViewModel: PaletteViewModel,
) {
    Column {
        PageHeader("Artists")
        ArtistView(navController, paletteViewModel)
    }
}