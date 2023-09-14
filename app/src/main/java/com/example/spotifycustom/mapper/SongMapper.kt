package com.example.spotifycustom.mapper

import com.example.spotifycustom.data.dto.Song
import com.example.spotifycustom.domain.model.DomainSong

fun Song.toDomainModel(): DomainSong {
    return DomainSong(
        id = this.id,
        name = this.name,
        albumId = this.albumId,
        artistId = this.artistId,
        imageUrl = this.imageUrl,
        songUrl = this.songUrl
    )
}

fun DomainSong.toDataModel(): Song {
    return Song(
        id = this.id,
        name = this.name,
        albumId = this.albumId,
        artistId = this.artistId,
        imageUrl = this.imageUrl,
        songUrl = this.songUrl
    )
}