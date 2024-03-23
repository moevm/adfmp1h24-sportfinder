package ru.moevm.sportfinder.screen.training

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class TrainingListItemVO(
    val trainingId: Long,
    val name: String,
    val tags: ImmutableList<String> = persistentListOf(),
    val distance: Float? = null,
    val temperature: Float? = null,
)
