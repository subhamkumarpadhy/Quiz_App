package com.example.quizical.feature_main.domain.models

data class EarningHistory(
    val points: Long = 0L,
    val type: EarningsType = EarningsType.None
)

enum class EarningsType {
    None, Plus, Minus
}