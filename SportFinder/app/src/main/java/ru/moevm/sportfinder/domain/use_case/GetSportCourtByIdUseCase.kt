package ru.moevm.sportfinder.domain.use_case

import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.moevm.sportfinder.domain.repository.SportCourtsRepository
import ru.moevm.sportfinder.domain.view_object.SportCourtAdvancedVO
import ru.moevm.sportfinder.domain.view_object.SportCourtVO
import javax.inject.Inject

class GetSportCourtByIdUseCase @Inject constructor(
    private val sportCourtsRepository: SportCourtsRepository
) {

    operator fun invoke(id: Int): Flow<SportCourtAdvancedVO> = flow {
        val result = sportCourtsRepository.getSportCourtById(id)
        val resultAsVO = SportCourtAdvancedVO(
            id = result.id,
            name = result.name,
            coordinates = LatLng(result.coordinates[0], result.coordinates[1]),
            address = result.address,
            district = result.district,
            tags = result.categories.split(", ")
        )

        emit(resultAsVO)
    }
}