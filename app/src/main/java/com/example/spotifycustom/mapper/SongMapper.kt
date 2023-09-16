package com.example.spotifycustom.mapper

import com.example.spotifycustom.data.dto.Song
import com.example.spotifycustom.domain.model.DomainSong

fun Song.toDomainModel(songId: String): DomainSong {
    return DomainSong(
        id = songId,
        name = this.name  ?: "",
        albumId = this.albumId  ?: "",
        artistId = this.artistId  ?: "",
        imageUrl = this.imageUrl  ?: "",
        songUrl = this.songUrl  ?: "",
        durationInSeconds = this.durationInSeconds ?: 0.toDouble(),
    )
}

fun DomainSong.toDataModel(): Song {
    return Song(
        name = this.name,
        albumId = this.albumId,
        artistId = this.artistId,
        imageUrl = this.imageUrl,
        songUrl = this.songUrl,
        durationInSeconds = this.durationInSeconds
    )
}