package ru.moevm.sportfinder.screen.sport_courts

import com.google.android.gms.maps.model.LatLng

data class SportCourtMapItem(
    val courtId: Int,
    val name: String,
    val coordinates: LatLng
)
