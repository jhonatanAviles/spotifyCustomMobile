package com.example.spotifycustom.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.google.android.exoplayer2.ExoPlayer

@Composable
fun MyFavoriteSongsScreen() {
    val context = LocalContext.current

    val player = remember {
        ExoPlayer.Builder(context).build()
    }

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
        Column {
            PageHeader(title = "Favorite Songs")
            FavoriteSongsView(player, songToReproduce, user, songsViewModel)
        }
        if (songToReproduce.value.songUrl.isNotEmpty()) AudioPlayer(player, songToReproduce)
    }
}