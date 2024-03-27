package ru.moevm.sportfinder.data.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "training")
data class TrainingData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id: Int = 0,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("author") val author: String,
    @ColumnInfo("tags") val tags: String,
    @ColumnInfo("description") val description: String,
)
