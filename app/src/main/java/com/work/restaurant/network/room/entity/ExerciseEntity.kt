package com.work.restaurant.network.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.work.restaurant.data.model.ExerciseModel
import com.work.restaurant.network.room.converter.ExerciseSetConverter

@Entity(tableName = "exercise")
data class ExerciseEntity(
    @PrimaryKey(autoGenerate = true)
    val exerciseNum: Int = 0,
    @ColumnInfo(name = "userId")
    val userId: String,
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "time")
    val time: String,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "exerciseName")
    val exerciseName: String,
    @TypeConverters(ExerciseSetConverter::class)
    val exerciseSetList: List<ExerciseSetResponse>
) {
    fun toExerciseModel(): ExerciseModel {
        val toExerciseSet = exerciseSetList.map {
            it.toExerciseSet()
        }
        return ExerciseModel(
            exerciseNum,
            userId,
            date,
            time,
            type,
            exerciseName,
            toExerciseSet
        )
    }

}

