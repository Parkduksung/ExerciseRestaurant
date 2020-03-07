package com.work.restaurant.data.model

import com.work.restaurant.network.room.entity.EatEntity
import com.work.restaurant.network.room.entity.ExerciseEntity

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

    fun toEatEntity(): EatEntity =
        EatEntity(
            eatNum,
            userId,
            date,
            time,
            type.toInt(),
            memo
        )

    fun toExerciseEntity(): ExerciseEntity {

        val toExerciseSetResponse = exerciseSet.map { it.toExerciseSetResponse() }

        return ExerciseEntity(
            exerciseNum,
            userId,
            date,
            time,
            type,
            exerciseSetName,
            toExerciseSetResponse
        )
    }

//    fun toSortDiaryModel(): DiaryModel {
//
//        val splitTime = time.split(" ")
//
//        val hour = if (splitTime[1].substring(
//                0,
//                splitTime[1].length - 1
//            ).toInt() / 10 == 0
//        ) {
//            "0${splitTime[1].substring(
//                0,
//                splitTime[1].length - 1
//            )}"
//        } else {
//            splitTime[1].substring(
//                0,
//                splitTime[1].length - 1
//            )
//        }
//
//        val minute = splitTime[2].substring(
//            0,
//            splitTime[2].length - 1
//        )
//        val convertTime = splitTime[0] + hour + minute
//
//        return DiaryModel(
//            eatNum,
//            exerciseNum,
//            userId,
//            kind,
//            date,
//            convertTime,
//            type,
//            memo,
//            exerciseSetName,
//            exerciseSet
//        )
//
//    }


    companion object {
        const val EAT = 0
        const val EXERCISE = 1
    }

}