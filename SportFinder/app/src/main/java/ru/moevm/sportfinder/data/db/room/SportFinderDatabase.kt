package ru.moevm.sportfinder.data.db.room

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.moevm.sportfinder.data.dto.RunningData
import ru.moevm.sportfinder.data.dto.TrainingData

@Database(
    entities = [RunningData::class, TrainingData::class],
    version = 1
)
abstract class SportFinderDatabase : RoomDatabase() {

    abstract fun runningDao(): RunningDao

    abstract fun trainingDao(): TrainingDao
}