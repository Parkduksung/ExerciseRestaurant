package com.work.restaurant.data.model

data class ExerciseModel(
    val date: String,
    val time: String,
    val type: String,
    val exerciseSetName: String,
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

