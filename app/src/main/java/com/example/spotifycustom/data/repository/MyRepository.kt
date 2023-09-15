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
            val domainArtists = artistsSnapshot.mapNotNull { docSnapshot ->
                val artist = docSnapshot.toObject(Artist::class.java)
                val artistId = docSnapshot.id // Get the Firestore document ID
                artist.toDomainModel(artistId) // Pass the ID to your mapping function
            }
            emit(domainArtists)
        }
    }


    fun getAlbums(): Flow<List<DomainAlbum>> {
        return flow {
            val albumsSnapshot = db.collection("albums").get().await()
            val domainAlbums = albumsSnapshot.mapNotNull { docSnapshot ->
                val album = docSnapshot.toObject(Album::class.java)
                val albumId = docSnapshot.id // Get the Firestore document ID for albums
                album.toDomainModel(albumId) // Pass the ID to your mapping function
            }
            emit(domainAlbums)
        }
    }

    fun getSongs(): Flow<List<DomainSong>> {
        return flow {
            val songsSnapshot = db.collection("songs").get().await()
            val domainSongs = songsSnapshot.mapNotNull { docSnapshot ->
                val song = docSnapshot.toObject(Song::class.java)
                val songId = docSnapshot.id // Get the Firestore document ID for songs
                song.toDomainModel(songId) // Pass the ID to your mapping function
            }
            emit(domainSongs)
        }
    }

    // Function to get albums by artist
    fun getAlbumsByArtist(artistId: String): Flow<List<DomainAlbum>> {
        return flow {
            val albumsSnapshot = db.collection("albums")
                .whereEqualTo("artistId", artistId)
                .get()
                .await()

            val domainAlbums = albumsSnapshot.mapNotNull { docSnapshot ->
                val album = docSnapshot.toObject(Album::class.java)
                val albumId = docSnapshot.id
                album.toDomainModel(albumId)
            }

            emit(domainAlbums)
        }
    }

    // Function to get songs by album
    fun getSongsByAlbum(albumId: String): Flow<List<DomainSong>> {
        return flow {
            val songsSnapshot = db.collection("songs")
                .whereEqualTo("albumId", albumId)
                .get()
                .await()

            val domainSongs = songsSnapshot.mapNotNull { docSnapshot ->
                val song = docSnapshot.toObject(Song::class.java)
                val songId = docSnapshot.id
                song.toDomainModel(songId)
            }

            emit(domainSongs)
        }
    }
}
