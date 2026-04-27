package com.example.learning_scaffold.auth

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

// Simple model for Authentication users
data class AppUser(
    val username: String = "",
    val email: String = "",
    val uid: String = ""
)

class AuthManager {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    /**
     * Create a new user with Email, Password, and a custom Username
     */
    fun createAccount(email: String, pass: String, username: String, onResult: (Boolean, String) -> Unit) {
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = auth.currentUser?.uid ?: ""
                    val userData = AppUser(username, email, uid)

                    // Store the username in Firestore linked to the Auth UID
                    db.collection("users").document(uid)
                        .set(userData)
                        .addOnSuccessListener {
                            onResult(true, "Account Created Successfully!")
                        }
                        .addOnFailureListener { e ->
                            onResult(false, "Profile Error: ${e.message}")
                        }
                } else {
                    onResult(false, "Auth Error: ${task.exception?.message}")
                }
            }
    }

    /**
     * Sign in existing user
     */
    fun login(email: String, pass: String, onResult: (Boolean, String) -> Unit) {
        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, "Login Successful")
                } else {
                    onResult(false, "Login Failed: ${task.exception?.message}")
                }
            }
    }

    fun logout() {
        auth.signOut()
    }
}