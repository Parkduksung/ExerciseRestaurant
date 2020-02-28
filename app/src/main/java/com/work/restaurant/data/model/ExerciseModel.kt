package com.work.restaurant.data.model

data class ExerciseModel(
    val exerciseNum: Int,
    val date: String,
    val time: String,
    val type: String,
    val exerciseSetName: String,
    val exerciseSet: List<ExerciseSet>
) {

    fun toDiaryModel(): DiaryModel =
        DiaryModel(
            0,
            exerciseNum,
            1,
            date,
            time,
            type,
            "",
            exerciseSetName,
            exerciseSet
        )

}

