package com.example.spotifycustom.data.repository

import com.example.spotifycustom.data.dto.Album
import com.example.spotifycustom.data.dto.Artist
import com.example.spotifycustom.data.dto.FavoriteSongPerUser
import com.example.spotifycustom.data.dto.Song
import com.example.spotifycustom.domain.model.DomainAlbum
import com.example.spotifycustom.domain.model.DomainArtist
import com.example.spotifycustom.domain.model.DomainSong
import com.example.spotifycustom.mapper.toDomainModel
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

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

    // Function to add a favorite song for a logged-in user
    suspend fun addFavoriteSongByUser(userId: String, songId: String): Boolean {
        // Check if the user already has the song marked as a favorite
        val isFavorite = checkIfSongIsFavorite(userId, songId)

        // If the song is already a favorite, return false (validation failed)
        if (isFavorite) {
            return false
        }

        // If the song is not a favorite, add it to the "favorites" collection
        val favoriteSong = FavoriteSongPerUser(userId, songId)
        db.collection("favorites")
            .add(favoriteSong)
            .await()

        return true // Validation passed, song added as a favorite
    }

    // Function to remove a song from favorites for a logged-in user
    suspend fun removeFavoriteSongByUser(userId: String, songId: String): Boolean {
        // Check if the song is marked as a favorite for the user
        val isFavorite = checkIfSongIsFavorite(userId, songId)

        // If the song is not a favorite, return false (validation failed)
        if (!isFavorite) {
            return false
        }

        // If the song is a favorite, remove it from the "favorites" collection
        val querySnapshot = db.collection("favorites")
            .whereEqualTo("userId", userId)
            .whereEqualTo("songId", songId)
            .get()
            .await()

        if (!querySnapshot.isEmpty) {
            // Delete the document representing the favorite
            db.collection("favorites")
                .document(querySnapshot.documents[0].id)
                .delete()
                .await()
        }

        return true // Removal succeeded
    }


    // Function to check if a song is already a favorite for the user
    suspend fun checkIfSongIsFavorite(userId: String, songId: String): Boolean {
        val querySnapshot = db.collection("favorites")
            .whereEqualTo("userId", userId)
            .whereEqualTo("songId", songId)
            .get()
            .await()

        return !querySnapshot.isEmpty
    }

    // Function to get favorite songs by logged-in user
    fun getFavoriteSongsByUser(userId: String): Flow<List<DomainSong>> {
        return flow {
            val favoriteSongsSnapshot = db.collection("favorites")
                .whereEqualTo("userId", userId)
                .get()
                .await()

            val songIds = favoriteSongsSnapshot.documents.map { document ->
                document.getString("songId") ?: ""
            }

            // Fetch the favorite songs based on the retrieved song IDs
            val songsSnapshot = db.collection("songs")
                .whereIn(FieldPath.documentId(), songIds)
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
