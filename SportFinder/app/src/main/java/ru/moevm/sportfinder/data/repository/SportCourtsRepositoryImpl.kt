package ru.moevm.sportfinder.data.repository

import ru.moevm.sportfinder.data.dto.SportCourtAdvancedDTO
import ru.moevm.sportfinder.data.dto.SportCourtsDTO
import ru.moevm.sportfinder.data.remote.ServerApi
import ru.moevm.sportfinder.domain.repository.SportCourtsRepository
import javax.inject.Inject

class SportCourtsRepositoryImpl @Inject constructor(
    private val api: ServerApi
) : SportCourtsRepository {
    override suspend fun getSportCourtsList(season: String, page: Int, count: Int): SportCourtsDTO? {
        return api.getSportCourts(season, page, count)
    }

    override suspend fun getSportCourtById(id: Int): SportCourtAdvancedDTO? {
        return api.getSportCourtById(id)
    }
}