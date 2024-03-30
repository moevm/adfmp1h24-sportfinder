package ru.moevm.sportfinder.screen.running

import com.google.android.gms.maps.model.LatLng
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class RunningCreateState(
    val title: String = "",
    val customTag: String = "",
    val initialSelectedTagIndices: ImmutableList<Int> = persistentListOf(),
    val listOfPoints: List<LatLng> = listOf(),
    val distance: Double = 0.0,
    val listOfTags: List<String> = listOf(),
    val availableTags: ImmutableList<String> = persistentListOf(),
    val isSelectTagDialogShown: Boolean = false
)
