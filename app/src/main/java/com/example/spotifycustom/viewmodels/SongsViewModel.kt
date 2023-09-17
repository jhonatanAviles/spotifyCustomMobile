package com.example.spotifycustom.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spotifycustom.data.repository.MyRepository
import com.example.spotifycustom.domain.model.DomainSong
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class SongsViewModel(private val repository: MyRepository) : ViewModel() {

    val songs: StateFlow<List<DomainSong>> = repository.getSongs().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(), // Optional: SharingStarted parameter
        initialValue = emptyList() // Optional: Initial value
    )

    suspend fun addFavoriteSongByUser(userId: String, songId: String): Boolean {
        return repository.addFavoriteSongByUser(userId, songId)
    }

    suspend fun checkIfSongIsFavorite(userId: String, songId: String): Boolean {
        return repository.checkIfSongIsFavorite(userId, songId)
    }

    suspend fun removeFavoriteSongByUser(userId: String, songId: String): Boolean {
        return repository.removeFavoriteSongByUser(userId, songId)
    }
}