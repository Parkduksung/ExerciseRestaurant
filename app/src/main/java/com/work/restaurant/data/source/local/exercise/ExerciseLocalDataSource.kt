package com.work.restaurant.data.source.local.exercise

import com.work.restaurant.data.model.ExerciseSet

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
}