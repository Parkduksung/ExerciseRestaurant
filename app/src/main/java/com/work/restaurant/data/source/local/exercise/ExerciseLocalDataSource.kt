package com.work.restaurant.data.source.local.exercise

import com.work.restaurant.data.model.ExerciseSet
import com.work.restaurant.network.room.entity.ExerciseEntity
import com.work.restaurant.network.room.entity.ExerciseSetResponse

interface ExerciseLocalDataSource {

    fun addExercise(
        userId: String,
        date: String,
        time: String,
        type: String,
        exerciseName: String,
        list: List<ExerciseSet>,
        callback: (isSuccess: Boolean) -> Unit
    )

    fun getAllList(
        userId: String,
        callback: (getList: List<ExerciseEntity>) -> Unit
    )

    fun getDataOfTheDay(
        userId: String,
        date: String,
        callback: (getList: List<ExerciseEntity>) -> Unit
    )

    fun deleteEat(
        data: ExerciseEntity,
        callback: (isSuccess: Boolean) -> Unit
    )

    fun updateExercise(
        changeTime: String,
        changeType: String,
        changeExerciseName: String,
        changeExerciseSet: List<ExerciseSetResponse>,
        currentId: String,
        currentExerciseNum: Int,
        callback: (isSuccess: Boolean) -> Unit
    )
}