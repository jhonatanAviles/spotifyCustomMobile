package com.example.spotifycustom.viewmodels.audio

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class AudioPlayerViewModel : ViewModel() {
    // Map of audio URLs to their corresponding progress states
    private val audioProgressMap: MutableMap<String, State<Float>> = mutableStateMapOf()

    // Map of audio URLs to their corresponding seek percentage states
    private val audioSeekPercentageMap: MutableMap<String, State<String>> = mutableStateMapOf()

    // Function to update the playback progress for a specific audio URL
    fun updateProgress(audioUrl: String, progress: Float) {
        audioProgressMap[audioUrl] = mutableFloatStateOf(progress)
    }

    // Function to get the playback progress state for a specific audio URL
    fun getCurrentProgressState(audioUrl: String): State<Float> {
        return audioProgressMap[audioUrl] ?: mutableFloatStateOf(0f)
    }

    // Function to set the seek percentage for a specific audio URL
    fun setSeekPercentage(audioUrl: String, newSeekPercentage: String) {
        audioSeekPercentageMap[audioUrl] = mutableStateOf(newSeekPercentage)
    }

    // Function to get the seek percentage state for a specific audio URL
    fun getSeekPercentageState(audioUrl: String): State<String> {
        return audioSeekPercentageMap[audioUrl] ?: mutableStateOf("")
    }
}

