package ru.moevm.sportfinder.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import ru.moevm.sportfinder.data.db.room.TrainingDao
import ru.moevm.sportfinder.data.dto.TrainingDTO
import ru.moevm.sportfinder.data.dto.TrainingData
import javax.inject.Inject


private const val DATA_SEPARATOR = ";"

class UseTrainingDatabaseUseCase @Inject constructor(
    private val trainingDao: TrainingDao,
    private val getProfileLoginUseCase: GetProfileLoginUseCase,
) {

    fun addTraining(
        name: String,
        tags: List<String>,
        description: String,
        author: String? = null
    ): Flow<Boolean> = flow {
        val login = getProfileLoginUseCase().first() ?: author
        if (login == null) {
            emit(false)
            return@flow
        }

        val trainingData = TrainingData(
            name = name,
            author = login,
            tags = tags.joinToString(separator = DATA_SEPARATOR),
            description = description
        )
        trainingDao.addTraining(trainingData)
        emit(true)
    }

    fun removeTraining(id: Int): Flow<Boolean> = flow {
        val training = trainingDao.getTrainingById(id)
        if (training == null) {
            emit(false)
            return@flow
        }

        trainingDao.removeTraining(training)
        emit(true)
    }

    fun getTrainings(): Flow<List<TrainingDTO>> = flow {
        val trainings = trainingDao.getTrainings()
        val trainingDtos = trainings.map { trainingData ->
            toTrainingDTO(trainingData)
        }
        emit(trainingDtos)
    }

    fun getTrainingById(id: Int): Flow<TrainingDTO?> = flow {
        val training = trainingDao.getTrainingById(id)
        val trainingDTO = if (training == null) {
            null
        } else {
            toTrainingDTO(training)
        }

        emit(trainingDTO)
    }

    fun getTrainingsByAuthor(author: String): Flow<List<TrainingDTO>> = flow {
        val trainings = trainingDao.getTrainingsByAuthor(author)
        val trainingDTOS = trainings.map { trainingData ->
            toTrainingDTO(trainingData)
        }
        emit(trainingDTOS)
    }

    private fun toTrainingDTO(training: TrainingData): TrainingDTO {
        return TrainingDTO(
            id = training.id,
            author = training.author,
            name = training.name,
            tags = training.tags.split(DATA_SEPARATOR).filter { it.isNotBlank() },
            description = training.description
        )
    }
}