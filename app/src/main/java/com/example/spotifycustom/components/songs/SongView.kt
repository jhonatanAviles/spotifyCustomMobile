package com.example.spotifycustom.components.songs

import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spotifycustom.data.repository.MyRepository
import com.example.spotifycustom.viewmodels.SongsViewModel
import com.example.spotifycustom.viewmodels.SongsViewModelFactory
import com.google.android.exoplayer2.ExoPlayer

@Composable
fun SongView(player: ExoPlayer) {

    val songsViewModel: SongsViewModel = viewModel(
        factory = SongsViewModelFactory(MyRepository())
    )

    val onAddToFavoritesClick: () -> Unit = {
        // Implement your logic for adding to favorites here
        // For example, you can update the ViewModel or perform any other action.
    }

    val songsState = songsViewModel.songs.collectAsState(initial = emptyList())
    val songs = songsState.value // Access the value of the State

    LazyColumn() {
        items(songs, key = { song ->
            song.id
        }) { song ->
            SongComponent(
                song = song,
                onAddToFavoritesClick = onAddToFavoritesClick,
                player = player
            )
        }
    }
}

@Composable
fun rememberMediaPlayer(): MediaPlayer {
    val mediaPlayer = MediaPlayer()
    mediaPlayer.setAudioAttributes(
        AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .build()
    )

    return remember { mediaPlayer }
}