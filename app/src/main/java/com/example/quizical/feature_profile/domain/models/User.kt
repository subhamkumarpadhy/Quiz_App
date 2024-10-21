package com.example.quizical.feature_profile.domain.models

data class User(
    val uid: String = "",
    val photo: String = "",
    val name: String = "",
    val phoneNumber: String = "",
    val points: Long = 0L,
)
