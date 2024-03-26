package ru.moevm.sportfinder.data.db.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.moevm.sportfinder.data.dto.RunningData

@Dao
interface RunningDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRunning(runningData: RunningData)

    @Delete
    suspend fun removeRunning(runningData: RunningData)

    @Query("SELECT * FROM running ORDER BY title")
    suspend fun getRunnings(): List<RunningData>

    @Query("SELECT * FROM running WHERE id = :id")
    suspend fun getRunningById(id: Int): RunningData?

    @Query("SELECT * FROM running WHERE author = :author")
    suspend fun getRunningsByAuthor(author: String): List<RunningData>
}