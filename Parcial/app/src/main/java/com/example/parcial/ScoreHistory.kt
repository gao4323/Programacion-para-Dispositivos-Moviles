package com.example.parcial

// Representa el historial de una partida
data class ScoreHistory(

    // Número de la partida jugada
    val gameNumber: Int,
    // Puntaje obtenido en esa partida
    val score: Int
)