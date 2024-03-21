package ru.moevm.sportfinder.screen.training

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class TrainingListState(
    val listOfTraining: ImmutableList<TrainingListItemVO> = persistentListOf(),
    val textForFilter: String = "",
    val isLoading: Boolean = false,
)