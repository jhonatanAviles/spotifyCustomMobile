package com.example.spotifycustom.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class PaletteViewModel: ViewModel() {

    private val _colorPalettes: MutableState<Map<String, Map<String, String>>> = mutableStateOf(emptyMap())
    val colorPalettes: State<Map<String, Map<String, String>>> = _colorPalettes

    fun setColorPalette(imageUrl: String, colors: Map<String, String>) {
        _colorPalettes.value = _colorPalettes.value + (imageUrl to colors)
    }

}