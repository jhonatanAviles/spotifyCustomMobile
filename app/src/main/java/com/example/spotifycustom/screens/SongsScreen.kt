package com.example.spotifycustom.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.spotifycustom.components.header.PageHeader
import com.example.spotifycustom.components.songs.AudioPlayer
import com.example.spotifycustom.components.songs.SongView
import com.google.android.exoplayer2.ExoPlayer

@Composable
fun SongsScreen(navController: NavHostController, albumId: String?) {
    val context = LocalContext.current

    val player = remember {
        ExoPlayer.Builder(context).build()
    }

    Column {
        PageHeader(title = "Songs")
        SongView(player)
        AudioPlayer(player)
    }
}