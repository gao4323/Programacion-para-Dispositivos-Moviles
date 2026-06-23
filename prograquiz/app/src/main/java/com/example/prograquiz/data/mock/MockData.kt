package com.example.prograquiz.data.mock

import com.example.prograquiz.model.*

object MockData {

    val currentUser = User(
        name = "Gabriel Jara",
        email = "gjaraz@ulasalle.edu.pe",
        bestScore = 95,
        matchesPlayed = 28,
        accuracy = 87
    )

    val levels = listOf(

        Level(
            title = "Básico",
            description = "Variables, tipos de datos y condicionales."
        ),

        Level(
            title = "Intermedio",
            description = "Bucles, arreglos y funciones."
        ),

        Level(
            title = "Avanzado",
            description = "Algoritmos y lógica compleja."
        )
    )

    val ranking = listOf(

        RankingUser(1, "Carlos", 980),
        RankingUser(2, "Ana", 950),
        RankingUser(3, "Gabriel", 920),
        RankingUser(4, "Maria", 900),
        RankingUser(5, "Luis", 870),
        RankingUser(6, "Kevin", 840)
    )

    val history = listOf(

        MatchHistory(
            date = "20/06/2026",
            level = "Básico",
            score = 80
        ),

        MatchHistory(
            date = "21/06/2026",
            level = "Intermedio",
            score = 90
        ),

        MatchHistory(
            date = "22/06/2026",
            level = "Avanzado",
            score = 70
        )
    )

    val questions = listOf(

        Question(
            id = 1,
            question = "¿Qué estructura se usa para repetir instrucciones?",
            options = listOf(
                "if",
                "for",
                "switch",
                "case"
            ),
            correctAnswer = 1,
            explanation = "El bucle for permite repetir instrucciones."
        ),

        Question(
            id = 2,
            question = "¿Cuál almacena múltiples valores?",
            options = listOf(
                "Variable",
                "Array",
                "Constante",
                "Clase"
            ),
            correctAnswer = 1,
            explanation = "Los arreglos almacenan varios valores."
        ),

        Question(
            id = 3,
            question = "¿Qué devuelve una función?",
            options = listOf(
                "Retorno",
                "Bucle",
                "Clase",
                "Objeto"
            ),
            correctAnswer = 0,
            explanation = "Una función puede devolver un valor mediante return."
        )
    )
}