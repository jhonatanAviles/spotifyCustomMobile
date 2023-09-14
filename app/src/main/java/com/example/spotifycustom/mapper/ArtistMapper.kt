package com.example.spotifycustom.mapper

import com.example.spotifycustom.data.dto.Artist
import com.example.spotifycustom.domain.model.DomainArtist

fun Artist.toDomainModel(): DomainArtist {
    return DomainArtist(
        id = this.id,
        name = this.name,
        genre = this.genre,
        imageUrl = this.imageUrl
    )
}

fun DomainArtist.toDataModel(): Artist {
    return Artist(
        id = this.id,
        name = this.name,
        genre = this.genre,
        imageUrl = this.imageUrl
    )
}