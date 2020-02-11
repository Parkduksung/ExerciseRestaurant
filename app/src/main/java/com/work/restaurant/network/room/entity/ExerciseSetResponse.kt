package com.work.restaurant.network.room.entity

import androidx.room.ColumnInfo
import com.work.restaurant.data.model.ExerciseSet

data class ExerciseSetResponse(
    @ColumnInfo(name = "exercise_set_kg")
    val exerciseSetKg: String,
    @ColumnInfo(name = "exercise_set_count")
    val exerciseSetCount: String
) {
    fun toExerciseSet(): ExerciseSet =
        ExerciseSet(
            exerciseSetKg,
            exerciseSetCount
        )
}

