package com.work.restaurant.data.model

data class DiaryModel(
    val kind: Int,
    val date: String,
    val time: String,
    val type: String,
    val memo: String?,
    val exerciseSetName: String?,
    val exerciseSet: List<ExerciseSet>?
) {

    companion object {
        const val EAT = 0
        const val EXERCISE = 1
    }

}