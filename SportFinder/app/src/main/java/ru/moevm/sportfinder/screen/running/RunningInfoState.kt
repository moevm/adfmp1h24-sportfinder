package ru.moevm.sportfinder.screen.running

import com.google.android.gms.maps.model.LatLng
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class RunningInfoState(
    val title: String = "",
    val listOfPoints: ImmutableList<LatLng> = persistentListOf(),
    val distance: Double = 0.0,
    val listOfTags: ImmutableList<String> = persistentListOf(),
)
