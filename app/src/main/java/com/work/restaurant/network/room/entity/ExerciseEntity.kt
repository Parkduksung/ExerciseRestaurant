package com.work.restaurant.network.room.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.work.restaurant.network.model.ExerciseSetResponse

@Entity(tableName = "exercise")
data class ExerciseEntity(
    @PrimaryKey(autoGenerate = true)
    val eatNum: Int = 0,
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "time")
    val time: String,
    @ColumnInfo(name = "type")
    val type: String,

    @Embedded
    val exerciseSetResponse: ExerciseSetResponse
)

