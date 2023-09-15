package com.example.spotifycustom.mapper

import com.example.spotifycustom.data.dto.Album
import com.example.spotifycustom.domain.model.DomainAlbum

fun Album.toDomainModel(albumId: String): DomainAlbum {
    return DomainAlbum(
        id = albumId,
        name = this.name ?: "",
        artistId = this.artistId ?: "",
        yearOfPublication = this.yearOfPublication ?: "",
        imageUrl = this.imageUrl ?: ""
    )
}

fun DomainAlbum.toDataModel(): Album {
    return Album(
        name = this.name,
        artistId = this.artistId,
        yearOfPublication = this.yearOfPublication,
        imageUrl = this.imageUrl
    )
}