package ru.moevm.sportfinder.domain.view_object

import com.google.android.gms.maps.model.LatLng

data class SportCourtAdvancedVO(
    val id: Int,
    val name: String,
    val coordinates: LatLng,
    val address: String,
    val tags: List<String>,
    val info: String
)
