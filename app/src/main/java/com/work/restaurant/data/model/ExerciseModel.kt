package com.work.restaurant.data.model

import com.google.gson.annotations.SerializedName

data class ExerciseModel(
    @SerializedName("date")
    val date: String,

    @SerializedName("time")
    val time: String,

    @SerializedName("type")
    val type: String,

    @SerializedName("exercise_name")
    val exerciseSetName: String,

    @SerializedName("set")
    val exerciseSet: List<ExerciseSet>

) {

    fun toDiaryModel(): DiaryModel =
        DiaryModel(
            1,
            date,
            time,
            type,
            null,
            exerciseSetName,
            exerciseSet
        )
}

