package com.work.restaurant.data.model

import android.os.Parcelable
import com.work.restaurant.network.room.entity.ExerciseEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ExerciseModel(
    val exerciseNum: Int,
    val userId: String,
    val date: String,
    val time: String,
    val type: String,
    val exerciseSetName: String,
    val exerciseSet: List<ExerciseSet>
) : Parcelable {
    fun toDiaryModel(): DiaryModel =
        DiaryModel(
            0,
            exerciseNum,
            userId,
            1,
            date,
            time,
            type,
            "",
            exerciseSetName,
            exerciseSet
        )

    fun toExerciseEntity(): ExerciseEntity =
        ExerciseEntity(
            exerciseNum,
            userId,
            date,
            time,
            type,
            exerciseSetName,
            exerciseSet.map { it.toExerciseSetResponse() }
        )

}

