package com.example.quizical.feature_quiz.domain.models

data class QuizzesModel(
    val questionsCount: Int = 0,
    val quizIcon: String = "",
    val quizTitle: String = "",
    val quizId: String? = null
)