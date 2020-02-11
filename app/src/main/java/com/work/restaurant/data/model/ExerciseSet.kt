package com.work.restaurant.data.model

import com.work.restaurant.network.room.entity.ExerciseSetResponse

data class ExerciseSet(
    val exerciseSetKg: String,
    val exerciseSetCount: String
) {
    fun toExerciseSetResponse(): ExerciseSetResponse =
        ExerciseSetResponse(
            exerciseSetKg,
            exerciseSetCount
        )

}