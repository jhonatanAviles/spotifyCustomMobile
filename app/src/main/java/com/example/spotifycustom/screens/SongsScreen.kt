package com.example.spotifycustom.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.spotifycustom.components.header.PageHeader
import com.example.spotifycustom.components.songs.AudioPlayer
import com.example.spotifycustom.components.songs.SongView
import com.example.spotifycustom.domain.model.DomainSong
import com.example.spotifycustom.utils.MutableStateDomainSongSaver
import com.google.android.exoplayer2.ExoPlayer

@Composable
fun SongsScreen(navController: NavHostController, albumId: String?) {
    val context = LocalContext.current

    val player = remember {
        ExoPlayer.Builder(context).build()
    }

    val songToReproduce = rememberSaveable(
        saver = MutableStateDomainSongSaver()
    ) { mutableStateOf(DomainSong("", "", "", "", "", "", 0.toDouble())) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            PageHeader(title = "Songs")
            SongView(player, songToReproduce)
        }
        if (songToReproduce.value.songUrl.isNotEmpty()) AudioPlayer(player, songToReproduce)
    }
}