package ru.moevm.sportfinder.data.dto

data class TrainingDTO(
    val id: Int = 0,
    val name: String,
    val author: String,
    val tags: List<String>,
    val description: String,
)
