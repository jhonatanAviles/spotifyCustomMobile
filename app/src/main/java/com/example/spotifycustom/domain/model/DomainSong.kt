package com.example.spotifycustom.domain.model

data class DomainSong(
    val id: String,
    val name: String,
    val albumId: String,
    val artistId: String,
    val imageUrl: String,
    val songUrl: String,
    val durationInSeconds: Double,
)
