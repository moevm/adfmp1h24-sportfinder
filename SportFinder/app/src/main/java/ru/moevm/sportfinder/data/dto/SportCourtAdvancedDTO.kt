package ru.moevm.sportfinder.data.dto


import com.google.gson.annotations.SerializedName

data class SportCourtAdvancedDTO(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("address")
    val address: String?,
    @SerializedName("area")
    val district: String?,
    @SerializedName("categories")
    val categories: String?,
    @SerializedName("coordinates")
    val coordinates: List<Double>?,
    @SerializedName("hours")
    val hours: String?,
    @SerializedName("phone")
    val phone: List<String>?,
    @SerializedName("season")
    val season: String?,
    @SerializedName("url")
    val url: List<String>?,
    @SerializedName("metro")
    val metro: String?,
)