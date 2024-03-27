package ru.moevm.sportfinder.screen.training

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class TrainingListItemVO(
    val trainingId: Int,
    val name: String,
    val tags: ImmutableList<String> = persistentListOf(),
    val temperature: Int? = null,
)
