package com.work.restaurant.data.model

import com.google.gson.annotations.SerializedName

data class ExerciseModel(
    @SerializedName("date")
    val date: String,

    @SerializedName("time")
    val time: String,

    @SerializedName("type")
    val type: String,

    @SerializedName("set")
    val exerciseSet: List<ExerciseSet>

)