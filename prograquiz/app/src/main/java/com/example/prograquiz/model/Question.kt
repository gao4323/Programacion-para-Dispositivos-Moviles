package com.example.prograquiz.model

data class Question(

    val id: Int,

    val question: String,

    val options: List<String>,

    val correctAnswer: Int,

    val explanation: String
)