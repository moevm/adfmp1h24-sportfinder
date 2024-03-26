package ru.moevm.sportfinder.data.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "running")
data class RunningData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id: Int = 0,
    @ColumnInfo("author") val author: String,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("tags") val tags: String, // "t1;t2;...;t3"
    @ColumnInfo("points") val points: String, // px1/py1;px2/py2;...;pxn/pyn"
)
