package ru.moevm.sportfinder.data.dto

import com.google.android.gms.maps.model.LatLng

data class RunningDTO(
    val id: Int = 0,
    val author: String,
    val title: String,
    val tags: List<String>,
    val dist: Double,
    val points: List<LatLng>,
)
