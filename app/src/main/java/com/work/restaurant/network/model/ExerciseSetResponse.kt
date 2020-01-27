package com.work.restaurant.network.model

import androidx.room.ColumnInfo
import com.work.restaurant.data.model.ExerciseSet

data class ExerciseSetResponse(
    @ColumnInfo(name = "exerciseSet_kg")
    val exerciseSetKg: String,
    @ColumnInfo(name = "exerciseSet_count")
    val exerciseSetCount: String
) {
    fun toExerciseSet(): ExerciseSet =
        ExerciseSet(
            exerciseSetKg,
            exerciseSetCount
        )
}

