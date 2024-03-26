package ru.moevm.sportfinder.domain.use_case

import android.util.Log
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.moevm.sportfinder.data.dto.SportCourtAdvancedDTO
import ru.moevm.sportfinder.domain.repository.SportCourtsRepository
import ru.moevm.sportfinder.domain.view_object.SportCourtAdvancedVO
import java.lang.Exception
import javax.inject.Inject

class GetSportCourtByIdUseCase @Inject constructor(
    private val sportCourtsRepository: SportCourtsRepository
) {

    operator fun invoke(id: Int): Flow<SportCourtAdvancedVO?> = flow {
        try {
            val result = sportCourtsRepository.getSportCourtById(id)
            val resultAsVO = SportCourtAdvancedVO(
                id = result.id ?: throw IllegalStateException("id is null"),
                name = result.name ?: throw IllegalStateException("name is null"),
                coordinates = result.coordinates?.let { LatLng(result.coordinates[0], result.coordinates[1]) } ?: throw IllegalStateException("coordinates is null"),
                address = result.address ?: "",
                tags = result.categories?.split(", ") ?: emptyList(),
                info = buildSportCourtInfo(result)
            )

            emit(resultAsVO)
        } catch (e: Exception) {
            Log.d("GetSportCourtByIdUseCase", "${e.message}")
            emit(null)
        }
    }

    private fun buildSportCourtInfo(result: SportCourtAdvancedDTO): String {
        val info = buildString {
            if (result.season?.isNotBlank() == true) {
                append("Подходит для времени года: ${result.season}\n")
            }
            if (result.district?.isNotBlank() == true) {
                append("Район: ${result.district}\n")
            }
            if (result.metro?.isNotBlank() == true) {
                append("Ближайшее метро: ${result.metro}\n")
            }
            if (result.hours?.isNotBlank() == true) {
                append("Время работы: ${result.hours}\n")
            }
            if (result.phone?.isNotEmpty() == true && result.phone.any { it.isNotEmpty() }) {
                append("Телефоны: ${result.phone.filter { it.isNotEmpty() }.joinToString(separator = ", ")}\n")
            }
            if (result.url?.isNotEmpty() == true && result.url.any { it.isNotEmpty() }) {
                append("Ссылки: ${result.url.filter { it.isNotEmpty() }.joinToString(separator = ", ")}\n")
            }
            if (this.lastOrNull() == '\n') {
                deleteCharAt(lastIndex)
            }
        }
        return info.ifEmpty { "Нет информации" }
    }
}