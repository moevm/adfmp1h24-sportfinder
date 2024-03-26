package ru.moevm.sportfinder.domain.use_case

import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.moevm.sportfinder.domain.repository.SportCourtsRepository
import ru.moevm.sportfinder.domain.view_object.SportCourtVO
import javax.inject.Inject

class GetSportCourtsUseCase @Inject constructor(
    private val sportCourtsRepository: SportCourtsRepository
) {

    operator fun invoke(): Flow<List<SportCourtVO>> = flow {
        val result = sportCourtsRepository.getSportCourtsList("Все", 1, 100)
        val resultAsVO = result.courtsData
            .filter { it.place.coordinates.size == 2 }
            .map { sportCourtDataDTO ->
                val sportCourt = sportCourtDataDTO.place
                SportCourtVO(
                    id = sportCourt.id,
                    name = sportCourt.name,
                    coordinates = LatLng(sportCourt.coordinates[0], sportCourt.coordinates[1]),
                    tags = sportCourt.categories.split(", ")
                )
            }

        emit(resultAsVO)
    }
}