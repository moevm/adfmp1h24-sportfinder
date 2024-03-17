package ru.moevm.sportfinder.screen.sport_courts

import com.google.android.gms.maps.model.LatLng
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class SportCourtInfoState(
    val courtName: String = "",
    val courtAddress: String = "",
    val courtInitialPoint: LatLng = LatLng(0.0, 0.0),
    val courtDistance: Double = 0.0,
    val courtWeatherTemperature: Int = 0,
    val courtAmountFavorites: Int = 0,
    val isFavorite: Boolean = false,
    val courtTags: ImmutableList<String> = persistentListOf(),
    val courtInfo: String = "",
    val courtLinkUrl: String = "",
)
