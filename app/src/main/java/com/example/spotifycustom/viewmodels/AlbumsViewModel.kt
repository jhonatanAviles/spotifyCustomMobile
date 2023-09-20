package com.example.spotifycustom.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spotifycustom.data.repository.MyRepository
import com.example.spotifycustom.domain.model.DomainAlbum
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class AlbumsViewModel(repository: MyRepository) : ViewModel() {

    val albums: StateFlow<List<DomainAlbum>> = repository.getAlbums().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(), // Optional: SharingStarted parameter
        initialValue = emptyList() // Optional: Initial value
    )

    val albumsByArtist: (artisId: String) -> StateFlow<List<DomainAlbum>> = { artisId ->
        repository.getAlbumsByArtist(artisId).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(), // Optional: SharingStarted parameter
            initialValue = emptyList() // Optional: Initial value
        )
    }
}