package com.example.spotifycustom.components.songs

import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spotifycustom.data.repository.MyRepository
import com.example.spotifycustom.domain.model.DomainSong
import com.example.spotifycustom.viewmodels.SongsViewModel
import com.example.spotifycustom.viewmodels.SongsViewModelFactory

@Composable
fun SongView(songToReproduce: MutableState<DomainSong>) {

    val songsViewModel: SongsViewModel = viewModel(
        factory = SongsViewModelFactory(MyRepository())
    )

    val songsState = songsViewModel.songs.collectAsState(initial = emptyList())
    val songs = songsState.value // Access the value of the State

    LazyColumn() {
        items(songs, key = { song ->
            song.id
        }) { song ->
            SongComponent(
                song = song,
                songToReproduce = songToReproduce,
                songsViewModel = songsViewModel,
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