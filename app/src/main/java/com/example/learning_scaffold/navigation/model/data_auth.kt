package com.example.learning_scaffold.navigation.model

data class UserAccount(
    val uid: String = "",
    val username: String = "",
    val email: String = "",
    val createdAt: Long = System.currentTimeMillis()
)