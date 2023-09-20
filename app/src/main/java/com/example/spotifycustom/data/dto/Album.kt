package com.example.spotifycustom.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Album(
    val name: String? = null,
    val artistId: String? = null,
    val yearOfPublication: String? = null,
    val imageUrl: String? = null
) : Parcelable
