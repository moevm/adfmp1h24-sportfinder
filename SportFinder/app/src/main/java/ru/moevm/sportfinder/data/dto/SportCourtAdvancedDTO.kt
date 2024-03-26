package ru.moevm.sportfinder.data.dto


import com.google.gson.annotations.SerializedName

data class SportCourtAdvancedDTO(
    @SerializedName("address")
    val address: String,
    @SerializedName("categories")
    val categories: String,
    @SerializedName("coordinates")
    val coordinates: List<Double>,
    @SerializedName("area")
    val district: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
)