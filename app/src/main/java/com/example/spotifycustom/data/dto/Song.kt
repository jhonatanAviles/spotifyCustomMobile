package com.example.spotifycustom.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Song(
    val name: String? = null,
    val albumId: String? = null,
    val artistId: String? = null,
    val imageUrl: String? = null,
    val songUrl: String? = null
) : Parcelable
