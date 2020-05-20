package com.work.restaurant.data.model

import android.os.Parcelable
import com.work.restaurant.network.room.entity.ExerciseSetResponse
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ExerciseSet(
    val exerciseSetKg: String,
    val exerciseSetCount: String
) : Parcelable {
    fun toExerciseSetResponse(): ExerciseSetResponse =
        ExerciseSetResponse(
            exerciseSetKg,
            exerciseSetCount
        )
}