package com.work.restaurant.data.source.local.exercise

import com.work.restaurant.data.model.ExerciseSet
import com.work.restaurant.network.room.entity.ExerciseEntity

interface ExerciseLocalDataSource {

    fun addExercise(
        userId: String,
        date: String,
        time: String,
        type: String,
        exerciseName: String,
        list: List<ExerciseSet>,
        callbackLocal: ExerciseLocalDataSourceCallback.AddExerciseCallback
    )

    fun getAllList(
        userId: String,
        callback: ExerciseLocalDataSourceCallback.GetAllList
    )

    fun getDataOfTheDay(
        userId: String,
        date: String,
        callback: ExerciseLocalDataSourceCallback.GetDataOfTheDay
    )

    fun deleteEat(
        data: ExerciseEntity,
        callback: ExerciseLocalDataSourceCallback.DeleteExerciseCallback
    )
}