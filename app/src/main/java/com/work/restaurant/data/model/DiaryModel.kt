package com.work.restaurant.data.model

data class DiaryModel(
    val eatNum: Int,
    val exerciseNum: Int,
    val userId: String,
    val kind: Int,
    val date: String,
    val time: String,
    val type: String,
    val memo: String,
    val exerciseSetName: String,
    val exerciseSet: List<ExerciseSet>
) {
    fun toExerciseModel(): ExerciseModel =
        ExerciseModel(
            exerciseNum, userId, date, time, type, exerciseSetName, exerciseSet
        )


    fun toEatModel(): EatModel =
        EatModel(eatNum, userId, date, time, type.toInt(), memo)


    companion object {
        const val EAT = 0
        const val EXERCISE = 1
    }

}