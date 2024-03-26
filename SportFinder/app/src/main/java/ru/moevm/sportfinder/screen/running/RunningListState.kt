package ru.moevm.sportfinder.screen.running

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class RunningListState(
    val listOfRunning: ImmutableList<RunningListItemVO> = persistentListOf(),
    val textForFilter: String = "",
    val isLoading: Boolean = false,
)