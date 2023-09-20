package com.example.spotifycustom.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class AuthViewModel: ViewModel() {
    private val _isLoggedIn = mutableStateOf(false)
    val isLoggedIn = _isLoggedIn
}