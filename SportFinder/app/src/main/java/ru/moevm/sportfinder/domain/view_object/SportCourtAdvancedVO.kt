package ru.moevm.sportfinder.domain.view_object

import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName

data class SportCourtAdvancedVO(
    val id: Int,
    val name: String,
    val coordinates: LatLng,
    val address: String,
    val district: String,
    val tags: List<String>,
)
