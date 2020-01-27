package com.work.restaurant.data.model

import com.google.gson.annotations.SerializedName
import com.work.restaurant.network.model.ExerciseSetResponse

data class ExerciseSet(
    @SerializedName("exerciseSet_kg")
    val exerciseSetKg: String,
    @SerializedName("exerciseSet_count")
    val exerciseSetCount: String
) {
    fun toExerciseSetResponse(): ExerciseSetResponse =
        ExerciseSetResponse(
            exerciseSetKg,
            exerciseSetCount
        )
    
}