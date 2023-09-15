package com.example.spotifycustom.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.spotifycustom.components.header.PageHeader
import com.example.spotifycustom.components.songs.AudioPlayer
import com.example.spotifycustom.components.songs.SongView

@Composable
fun SongsScreen(navController: NavHostController, albumId: String?) {
    Column {
        PageHeader(title = "Songs")
        SongView()
        AudioPlayer()
    }
}