package ru.moevm.sportfinder.domain.use_case

import android.util.Log
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
        try {
            val result = sportCourtsRepository.getSportCourtsList("Все", 1, 100)
            val resultAsVO = result?.courtsData
                ?.filter { it.place?.coordinates?.size == 2 && it.place.id != null && it.place.name != null }
                ?.map { sportCourtDataDTO ->
                    val sportCourt = sportCourtDataDTO.place
                    SportCourtVO(
                        id = requireNotNull(sportCourt?.id),
                        name = requireNotNull(sportCourt?.name),
                        coordinates = LatLng(requireNotNull(sportCourt?.coordinates)[0], requireNotNull(sportCourt?.coordinates)[1]),
                        tags = sportCourt?.categories?.split(", ") ?: emptyList()
                    )
                } ?: listOf()

            emit(resultAsVO)
        } catch (e: Exception) {
            Log.d("GetSportCourtsUseCase", "${e.message}")
            emit(listOf())
        }
    }
}