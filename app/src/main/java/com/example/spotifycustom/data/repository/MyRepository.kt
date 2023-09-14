package com.example.spotifycustom.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import com.google.firebase.firestore.FirebaseFirestore
import com.example.spotifycustom.data.dto.Album
import com.example.spotifycustom.data.dto.Artist
import com.example.spotifycustom.data.dto.Song
import com.example.spotifycustom.domain.model.DomainAlbum
import com.example.spotifycustom.domain.model.DomainArtist
import com.example.spotifycustom.domain.model.DomainSong
import com.example.spotifycustom.mapper.toDomainModel

class MyRepository {

    private val db = FirebaseFirestore.getInstance()

    fun getArtists(): Flow<List<DomainArtist>> {
        return flow {
            val artistsSnapshot = db.collection("artists").get().await()
            val artists = artistsSnapshot.toObjects(Artist::class.java)
            val domainArtists = artists.map { it.toDomainModel() }
            emit(domainArtists)
        }
    }

    fun getAlbums(): Flow<List<DomainAlbum>> {
        return flow {
            val albumsSnapshot = db.collection("albums").get().await()
            val albums = albumsSnapshot.toObjects(Album::class.java)
            val domainAlbums = albums.map { it.toDomainModel() }
            emit(domainAlbums)
        }
    }

    fun getSongs(): Flow<List<DomainSong>> {
        return flow {
            val songsSnapshot = db.collection("songs").get().await()
            val songs = songsSnapshot.toObjects(Song::class.java)
            val domainSongs = songs.map { it.toDomainModel() }
            emit(domainSongs)
        }
    }
}
