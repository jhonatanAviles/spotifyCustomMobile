package com.example.spotifycustom.mapper

import com.example.spotifycustom.data.dto.Artist
import com.example.spotifycustom.domain.model.DomainArtist

fun Artist.toDomainModel(artistId: String): DomainArtist {
    return DomainArtist(
        id = artistId,
        name = this.name ?: "",
        genre = this.genre ?: "",
        imageUrl = this.imageUrl ?: ""
    )
}

fun DomainArtist.toDataModel(): Artist {
    return Artist(
        name = this.name,
        genre = this.genre,
        imageUrl = this.imageUrl
    )
}