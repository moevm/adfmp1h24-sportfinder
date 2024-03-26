package ru.moevm.sportfinder.screen.running

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class RunningListItemVO(
    val runningId: Int,
    val name: String,
    val tags: ImmutableList<String> = persistentListOf(),
    val distance: Double? = null,
    val temperature: Int? = null,
)
