package com.example.learning_scaffold.navigation.navScreen.auth

import android.content.Context
import com.example.learning_scaffold.navigation.model.UserAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AuthRepository(private val context: Context) {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    /**
     * REGISTER with Email, Password, and Username
     */
    fun signUpWithEmail(email: String, pass: String, username: String, onResult: (Boolean, String?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = auth.currentUser?.uid ?: ""
                    val userAccount = UserAccount(uid, username, email)

                    // Store the username in Firestore linked to the UID
                    db.collection("users").document(uid).set(userAccount)
                        .addOnSuccessListener {
                            onResult(true, "Registration Successful")
                        }
                        .addOnFailureListener { e ->
                            onResult(false, e.message)
                        }
                } else {
                    onResult(false, task.exception?.message)
                }
            }
    }

    /**
     * LOGIN with Email and Password
     */
    fun loginWithEmail(email: String, pass: String, onResult: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, "Welcome Back!")
                } else {
                    onResult(false, task.exception?.message)
                }
            }
    }

    /**
     * FETCH Profile (To get the username after login)
     */
    fun getUserProfile(uid: String, onUserFetched: (UserAccount?) -> Unit) {
        db.collection("users").document(uid).get()
            .addOnSuccessListener { doc ->
                val user = doc.toObject(UserAccount::class.java)
                onUserFetched(user)
            }
            .addOnFailureListener {
                onUserFetched(null)
            }
    }

    fun logout() {
        auth.signOut()
    }
}