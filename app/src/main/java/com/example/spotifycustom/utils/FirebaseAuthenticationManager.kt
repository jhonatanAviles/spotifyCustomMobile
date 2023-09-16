package com.example.spotifycustom.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class FirebaseAuthenticationManager {

    private val auth = FirebaseAuth.getInstance()

    // Sign Up Function
    fun signUpWithEmail(email: String, password: String, onComplete: (FirebaseUser?) -> Unit, onError: (Exception) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    onComplete(user)
                } else {
                    val exception = task.exception
                    if (exception != null) {
                        onError(exception)
                    }
                }
            }
    }

    // Login Function
    fun loginWithEmail(email: String, password: String, onComplete: (FirebaseUser?) -> Unit, onError: (Exception) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    onComplete(user)
                } else {
                    val exception = task.exception
                    if (exception != null) {
                        onError(exception)
                    }
                }
            }
    }

    // Sign Out Function
    fun signOut() {
        auth.signOut()
    }
}
