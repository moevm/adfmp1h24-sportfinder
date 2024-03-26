package ru.moevm.sportfinder.domain.view_object

import com.google.android.gms.maps.model.LatLng

data class SportCourtVO(
    val id: Int,
    val name: String,
    val coordinates: LatLng,
    val tags: List<String>,
)
