package com.example.spotifycustom.utils

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import com.example.spotifycustom.domain.model.DomainSong

class MutableStateDomainSongSaver : Saver<MutableState<DomainSong>, Map<String, Any>> {
    override fun restore(value: Map<String, Any>): MutableState<DomainSong> {
        val domainSong = DomainSong(
            id = value["id"].toString(),
            name = value["name"].toString(),
            albumId = value["albumId"].toString(),
            artistId = value["artistId"].toString(),
            imageUrl = value["imageUrl"].toString(),
            songUrl = value["songUrl"].toString(),
            durationInSeconds = value["durationInSeconds"].toString().toDouble()
        )
        return mutableStateOf(domainSong)
    }

    override fun SaverScope.save(value: MutableState<DomainSong>): Map<String, Any> {
        val domainSong = value.value
        return domainSong.toMap()// Convert the DomainSong to a string (e.g., to JSON)
    }
}

fun DomainSong.toMap(): Map<String, Any> {
    return mapOf(
        "id" to id,
        "name" to name,
        "albumId" to albumId,
        "artistId" to artistId,
        "imageUrl" to imageUrl,
        "songUrl" to songUrl,
        "durationInSeconds" to durationInSeconds
    )
}
