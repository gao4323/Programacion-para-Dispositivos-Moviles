package com.example.prograquiz.model

data class User(
    val name: String,
    val email: String,
    val bestScore: Int,
    val matchesPlayed: Int,
    val accuracy: Int
)