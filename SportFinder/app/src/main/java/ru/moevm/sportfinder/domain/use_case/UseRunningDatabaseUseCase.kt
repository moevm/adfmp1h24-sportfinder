package ru.moevm.sportfinder.domain.use_case

import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import ru.moevm.sportfinder.common.MapTools
import ru.moevm.sportfinder.data.db.room.RunningDao
import ru.moevm.sportfinder.data.dto.RunningDTO
import ru.moevm.sportfinder.data.dto.RunningData
import javax.inject.Inject

private const val DATA_SEPARATOR = ";"
private const val SUBDATA_SEPARATOR = "/"

class UseRunningDatabaseUseCase @Inject constructor(
    private val runningDao: RunningDao,
    private val getProfileLoginUseCase: GetProfileLoginUseCase,
) {

    fun addRunning(
        title: String,
        tags: List<String>,
        points: List<LatLng>,
        author: String? = null,
    ): Flow<Boolean> = flow {
        val login = getProfileLoginUseCase().first() ?: author
        if (login == null) {
            emit(false)
            return@flow
        }

        val runningData = RunningData(
            author = login,
            title = title,
            tags = tags.joinToString(separator = DATA_SEPARATOR),
            points = points
                .joinToString(separator = DATA_SEPARATOR) { point ->
                    "${point.latitude}$SUBDATA_SEPARATOR${point.longitude}"
                }
        )
        runningDao.addRunning(runningData)
        emit(true)
    }

    fun removeRunning(id: Int): Flow<Boolean> = flow {
        val running = runningDao.getRunningById(id)
        if (running == null) {
            emit(false)
            return@flow
        }

        runningDao.removeRunning(running)
        emit(true)
    }

    fun getRunnings(): Flow<List<RunningDTO>> = flow {
        val runnings = runningDao.getRunnings()
        val runningDtos = runnings.map { runningData ->
            toRunningDTO(runningData)
        }
        emit(runningDtos)
    }

    fun getRunningById(id: Int): Flow<RunningDTO?> = flow {
        val running = runningDao.getRunningById(id)
        val runningDto = if (running == null) {
            null
        } else {
            toRunningDTO(running)
        }

        emit(runningDto)
    }

    fun getRunningsByAuthor(author: String): Flow<List<RunningDTO>> = flow {
        val runnings = runningDao.getRunningsByAuthor(author)
        val runningDtos = runnings.map { runningData ->
            toRunningDTO(runningData)
        }
        emit(runningDtos)
    }

    private fun toRunningDTO(running: RunningData): RunningDTO {
        val points = running.points.split(DATA_SEPARATOR).filter { it.isNotBlank() }.map {
            val latLng = it.split(SUBDATA_SEPARATOR)
                .filter { points -> points.isNotBlank() }
                .map { point -> point.toDouble() }
            LatLng(latLng[0], latLng[1])
        }
        return RunningDTO(
            id = running.id,
            author = running.author,
            title = running.title,
            tags = running.tags.split(DATA_SEPARATOR).filter { it.isNotBlank() },
            dist = MapTools.calcDistance(points),
            points = points
        )
    }
}