package com.programacion.prograquiz.model

enum class DifficultyLevel(val label: String) {
    BASICO("Básico"),
    INTERMEDIO("Intermedio"),
    AVANZADO("Avanzado")
}

data class User(
    val id: String,
    val username: String,
    val email: String,
    val avatarInitials: String,
    val bestScore: Int,
    val totalGames: Int,
    val totalCorrect: Int,
    val totalQuestions: Int,
    val favoriteLevel: DifficultyLevel
)

data class Question(
    val id: Int,
    val text: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String,
    val level: DifficultyLevel
)

data class GameHistory(
    val id: Int,
    val date: String,
    val level: DifficultyLevel,
    val score: Int,
    val correct: Int,
    val total: Int
)

data class RankingEntry(
    val position: Int,
    val username: String,
    val score: Int,
    val level: DifficultyLevel,
    val avatarInitials: String
)

data class QuizState(
    val questions: List<Question>,
    val currentIndex: Int = 0,
    val selectedOptionIndex: Int? = null,
    val answered: Boolean = false,
    val correctCount: Int = 0,
    val wrongCount: Int = 0
) {
    val currentQuestion: Question get() = questions[currentIndex]
    val isLastQuestion: Boolean get() = currentIndex == questions.size - 1
    val progress: Float get() = (currentIndex + 1).toFloat() / questions.size.toFloat()
    val finalScore: Int get() = (correctCount.toFloat() / questions.size * 100).toInt()
}
