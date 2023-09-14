package com.example.spotifycustom.mapper

import com.example.spotifycustom.data.dto.Album
import com.example.spotifycustom.domain.model.DomainAlbum

fun Album.toDomainModel(): DomainAlbum {
    return DomainAlbum(
        id = this.id,
        name = this.name,
        artistId = this.artistId,
        yearOfPublication = this.yearOfPublication,
        imageUrl = this.imageUrl
    )
}

fun DomainAlbum.toDataModel(): Album {
    return Album(
        id = this.id,
        name = this.name,
        artistId = this.artistId,
        yearOfPublication = this.yearOfPublication,
        imageUrl = this.imageUrl
    )
}