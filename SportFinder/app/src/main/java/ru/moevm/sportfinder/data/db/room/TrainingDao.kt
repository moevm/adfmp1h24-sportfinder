package ru.moevm.sportfinder.data.db.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.moevm.sportfinder.data.dto.TrainingData

@Dao
interface TrainingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTraining(trainingData: TrainingData)

    @Delete
    suspend fun removeTraining(trainingData: TrainingData)

    @Query("SELECT * FROM training ORDER BY name")
    suspend fun getTrainings(): List<TrainingData>

    @Query("SELECT * FROM training WHERE id = :id")
    suspend fun getTrainingById(id: Int): TrainingData?

    @Query("SELECT * FROM training WHERE author = :author")
    suspend fun getTrainingsByAuthor(author: String): List<TrainingData>
}