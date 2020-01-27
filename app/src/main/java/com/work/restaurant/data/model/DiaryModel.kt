package com.work.restaurant.data.model

import com.google.gson.annotations.SerializedName

data class DiaryModel(
    @SerializedName("kind")
    val kind: Int,

    @SerializedName("date")
    val date: String,

    @SerializedName("time")
    val time: String,

    @SerializedName("type")
    val type: String,

    @SerializedName("memo")
    val memo: String?,

    @SerializedName("exercise_name")
    val exerciseSetName: String?,

    @SerializedName("set")
    val exerciseSet: List<ExerciseSet>?
) {

    companion object {
        const val EAT = 0
        const val EXERCISE = 1
    }

}