package ru.moevm.sportfinder.domain.repository

import ru.moevm.sportfinder.data.dto.SportCourtAdvancedDTO
import ru.moevm.sportfinder.data.dto.SportCourtsDTO

interface SportCourtsRepository {

    suspend fun getSportCourtsList(season: String, page: Int, count: Int): SportCourtsDTO

    suspend fun getSportCourtById(id: Int): SportCourtAdvancedDTO
}