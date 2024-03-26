package ru.moevm.sportfinder.data.remote

import retrofit2.http.GET
import retrofit2.http.Query
import ru.moevm.sportfinder.data.dto.SportCourtAdvancedDTO
import ru.moevm.sportfinder.data.dto.SportCourtsDTO

interface ServerApi {
    @GET("sportgrounds")
    suspend fun getSportCourts(
        @Query("season") season: String,
        @Query("page") page: Int,
        @Query("count") count: Int,
    ): SportCourtsDTO

    @GET("sportgrounds/id")
    suspend fun getSportCourtById(
        @Query("id") id: Int
    ): SportCourtAdvancedDTO
}