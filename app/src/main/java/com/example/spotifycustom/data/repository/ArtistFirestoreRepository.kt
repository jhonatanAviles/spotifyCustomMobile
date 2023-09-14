package com.example.spotifycustom.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import com.google.firebase.firestore.FirebaseFirestore
import com.example.spotifycustom.data.model.Artist

class ArtistFirestoreRepository(private val db: FirebaseFirestore) {

    fun getArtists(): Flow<List<Artist>> {
        return flow {
            val artistsSnapshot = db.collection("artists").get().await()
            val artists = artistsSnapshot.toObjects(Artist::class.java)
            emit(artists)
        }
    }
}