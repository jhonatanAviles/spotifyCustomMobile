package com.example.spotifycustom.components.songs

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.spotifycustom.domain.model.DomainSong
import com.example.spotifycustom.viewmodels.SongsViewModel

@Composable
fun SongView(
    songToReproduce: MutableState<DomainSong>,
    albumId: String?,
    songsViewModel: SongsViewModel
) {

    var songs by remember { mutableStateOf(emptyList<DomainSong>()) }

    val songsState =
        if (albumId == null) songsViewModel.songs.collectAsState(initial = emptyList())
        else {
            // Use remember to store StateFlow based on albumId
            val stateFlow = remember(albumId) {
                songsViewModel.songsByAlbum(albumId)
            }
            stateFlow.collectAsState(initial = emptyList())
        }
    songs = songsState.value

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