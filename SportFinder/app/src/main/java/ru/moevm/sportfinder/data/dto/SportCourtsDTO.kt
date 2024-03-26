package ru.moevm.sportfinder.data.dto


import com.google.gson.annotations.SerializedName

data class SportCourtsDTO(
    @SerializedName("count")
    val countCourts: Int, // всего площадок
    @SerializedName("data")
    val courtsData: List<SportCourtDataDTO>, // данные о площадках
    @SerializedName("page")
    val page: Int, // номер страницы площадки в запросе
    @SerializedName("season")
    val season: String, // сезон площадки
    @SerializedName("size")
    val size: Int // количество площадок в запросе
)