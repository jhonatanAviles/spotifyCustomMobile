package com.example.spotifycustom.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.spotifycustom.data.repository.MyRepository

class AlbumsViewModelFactory(private val repository: MyRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlbumsViewModel::class.java)) {
            return AlbumsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}