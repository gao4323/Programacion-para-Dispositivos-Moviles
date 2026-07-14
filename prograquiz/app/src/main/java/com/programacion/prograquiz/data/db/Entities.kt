package com.programacion.prograquiz.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Tabla de usuarios registrados.
 * El password se guarda como hash simple (MD5) — suficiente para proyecto académico.
 */
@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val username: String,
    val email: String,
    val passwordHash: String,       // hash MD5 del password
    val bestScore: Int = 0,
    val totalGames: Int = 0,
    val totalCorrect: Int = 0,
    val totalQuestions: Int = 0
)

/**
 * Tabla del historial de partidas.
 */
@Entity(tableName = "game_history")
data class GameHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: Int,                // FK al usuario
    val date: String,
    val level: String,             // "BASICO", "INTERMEDIO", "AVANZADO"
    val score: Int,
    val correct: Int,
    val total: Int
)

/**
 * Tabla del ranking (una entrada por usuario con su mejor puntaje).
 * Se actualiza cada vez que el usuario supera su récord.
 */
@Entity(tableName = "ranking")
data class RankingEntity(
    @PrimaryKey
    val userId: Int,
    val username: String,
    val bestScore: Int,
    val bestLevel: String
)
