package ru.moevm.sportfinder.screen.running

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class RunningListItemVO(
    val runningId: Long,
    val name: String,
    val tags: ImmutableList<String> = persistentListOf(),
    val distance: Float? = null,
    val temperature: Float? = null,
)
