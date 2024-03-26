package ru.moevm.sportfinder.screen.training

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class TrainingInfoState(
    val name: String = "",
    val tags: ImmutableList<String> = persistentListOf(),
    val description: String = "",
)
