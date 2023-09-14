package com.example.spotifycustom.data.dto

data class Song(
    val id: String,
    val name: String,
    val albumId: String,
    val artistId: String,
    val imageUrl: String,
    val songUrl: String
)
