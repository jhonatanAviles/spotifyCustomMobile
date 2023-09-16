package com.example.spotifycustom.utils

fun formatSecondsToMinutesAndSeconds(seconds: Double): String {
    val minutes = seconds.toInt() / 60
    val remainingSeconds = (seconds % 60).toInt()
    return String.format("%d:%02d", minutes, remainingSeconds)
}

