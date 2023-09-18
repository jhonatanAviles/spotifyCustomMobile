package com.example.spotifycustom.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.spotifycustom.components.header.PageHeader
import com.example.spotifycustom.components.songs.AudioPlayer
import com.example.spotifycustom.components.songs.SongView
import com.example.spotifycustom.domain.model.DomainSong
import com.example.spotifycustom.utils.MutableStateDomainSongSaver

@Composable
fun SongsScreen(navController: NavHostController, albumId: String?) {

    val songToReproduce = rememberSaveable(
        saver = MutableStateDomainSongSaver()
    ) { mutableStateOf(DomainSong("", "", "", "", "", "", 0.toDouble())) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(5f)) {
            PageHeader(title = "Songs")
            SongView(songToReproduce)
        }
        if (songToReproduce.value.songUrl.isNotEmpty())
            Column(modifier = Modifier.weight(1f)) {
                AudioPlayer(songToReproduce)
            }
    }
}