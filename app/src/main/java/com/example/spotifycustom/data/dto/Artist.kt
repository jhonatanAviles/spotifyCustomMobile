package com.example.spotifycustom.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Artist(
    val name: String? = null,
    val genre: String? = null,
    val imageUrl: String? = null,
) : Parcelable