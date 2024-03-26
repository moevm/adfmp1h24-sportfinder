package ru.moevm.sportfinder.data.dto


import com.google.gson.annotations.SerializedName

data class SportCourtDataDTO(
    @SerializedName("place")
    val place: SportCourtPlaceDTO
)