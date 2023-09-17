package com.example.spotifycustom.components.songs

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.spotifycustom.domain.model.DomainSong
import com.example.spotifycustom.viewmodels.SongsViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.firebase.auth.FirebaseUser

@Composable
fun FavoriteSongsView(
    player: ExoPlayer,
    songToReproduce: MutableState<DomainSong>,
    user: FirebaseUser?,
    songsViewModel: SongsViewModel,
) {
    val songsState by songsViewModel.getFavoriteSongs(user!!.uid).collectAsState(initial = emptyList())

    LazyColumn() {
        items(songsState, key = { song ->
            song.id
        }) { song ->
            SongComponent(
                song = song,
                player = player,
                songToReproduce = songToReproduce,
                songsViewModel = songsViewModel,
            )
        }
    }
}