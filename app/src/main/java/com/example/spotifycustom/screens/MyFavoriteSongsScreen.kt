package com.example.spotifycustom.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spotifycustom.components.header.PageHeader
import com.example.spotifycustom.components.songs.AudioPlayer
import com.example.spotifycustom.components.songs.FavoriteSongsView
import com.example.spotifycustom.data.repository.MyRepository
import com.example.spotifycustom.domain.model.DomainSong
import com.example.spotifycustom.utils.FirebaseAuthenticationManager
import com.example.spotifycustom.utils.MutableStateDomainSongSaver
import com.example.spotifycustom.viewmodels.SongsViewModel
import com.example.spotifycustom.viewmodels.SongsViewModelFactory

@Composable
fun MyFavoriteSongsScreen() {

    val songToReproduce = rememberSaveable(
        saver = MutableStateDomainSongSaver()
    ) { mutableStateOf(DomainSong("", "", "", "", "", "", 0.toDouble())) }

    val songsViewModel: SongsViewModel = viewModel(
        factory = SongsViewModelFactory(MyRepository())
    )

    Log.d("RENDERS", "RENDERING...")

    val authManager = FirebaseAuthenticationManager()

    val user = authManager.getUser()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(5f)) {
            PageHeader(title = "Favorite Songs")
            FavoriteSongsView(songToReproduce, user, songsViewModel)
        }
        if (songToReproduce.value.songUrl.isNotEmpty())
            Column(modifier = Modifier.weight(1f)) {
                AudioPlayer(songToReproduce)
            }
    }
}