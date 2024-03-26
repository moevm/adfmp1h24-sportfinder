package ru.moevm.sportfinder.data.dto


import com.google.gson.annotations.SerializedName

data class SportCourtPlaceDTO(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("coordinates")
    val coordinates: List<Double>?,
    @SerializedName("categories")
    val categories: String?,
)