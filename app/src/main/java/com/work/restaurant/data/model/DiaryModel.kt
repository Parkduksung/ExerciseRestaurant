package com.work.restaurant.data.model

import com.work.restaurant.network.room.entity.EatEntity
import com.work.restaurant.network.room.entity.ExerciseEntity

data class DiaryModel(
    val eatNum: Int,
    val exerciseNum: Int,
    val kind: Int,
    val date: String,
    val time: String,
    val type: String,
    val memo: String,
    val exerciseSetName: String,
    val exerciseSet: List<ExerciseSet>
) {

    fun toEatEntity(): EatEntity =
        EatEntity(
            eatNum,
            date,
            time,
            type.toInt(),
            memo
        )

    fun toExerciseEntity(): ExerciseEntity {

        val toExerciseSetResponse = exerciseSet.map { it.toExerciseSetResponse() }

        return ExerciseEntity(
            exerciseNum,
            date,
            time,
            type,
            exerciseSetName,
            toExerciseSetResponse
        )
    }


    companion object {
        const val EAT = 0
        const val EXERCISE = 1
    }

}