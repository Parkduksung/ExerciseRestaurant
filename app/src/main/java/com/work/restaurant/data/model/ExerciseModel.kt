package com.work.restaurant.data.model

data class ExerciseModel(
    val exerciseNum: Int,
    val userId: String,
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
            userId,
            1,
            date,
            time,
            type,
            "",
            exerciseSetName,
            exerciseSet
        )

}

