package com.work.restaurant.data.source.local.exercise

import com.work.restaurant.data.model.ExerciseSet
import com.work.restaurant.network.room.entity.ExerciseEntity

interface ExerciseLocalDataSource {

    fun addExercise(
        date: String,
        time: String,
        type: String,
        exerciseName: String,
        list: List<ExerciseSet>,
        callbackLocal: ExerciseLocalDataSourceCallback.AddExerciseCallback
    )

    fun getAllList(
        callback: ExerciseLocalDataSourceCallback.GetAllList
    )

    fun getDataOfTheDay(
        date: String,
        callback: ExerciseLocalDataSourceCallback.GetDataOfTheDay
    )

    fun deleteEat(
        data: ExerciseEntity,
        callback: ExerciseLocalDataSourceCallback.DeleteExerciseCallback
    )
}