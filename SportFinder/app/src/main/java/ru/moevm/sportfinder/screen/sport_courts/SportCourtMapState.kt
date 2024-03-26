package ru.moevm.sportfinder.screen.sport_courts

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class SportCourtMapState(
    val listOfSportCourt: ImmutableList<SportCourtMapItem> = persistentListOf(),
    val textForFilter: String = "",
)