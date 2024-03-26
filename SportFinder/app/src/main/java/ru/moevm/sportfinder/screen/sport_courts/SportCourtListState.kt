package ru.moevm.sportfinder.screen.sport_courts

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class SportCourtListState(
    val listOfSportCourt: ImmutableList<SportCourtListItem> = persistentListOf(),
    val textForFilter: String = "",
    val isLoading: Boolean = false,
)
