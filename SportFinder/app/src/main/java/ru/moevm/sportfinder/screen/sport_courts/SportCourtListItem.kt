package ru.moevm.sportfinder.screen.sport_courts

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class SportCourtListItem(
    val courtId: Int,
    val name: String,
    val tags: ImmutableList<String> = persistentListOf(),
    val temperature: Float? = null,
)
