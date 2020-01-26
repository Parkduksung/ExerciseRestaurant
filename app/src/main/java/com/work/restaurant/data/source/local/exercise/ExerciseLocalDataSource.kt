package com.work.restaurant.data.source.local.exercise

import com.work.restaurant.data.model.ExerciseSet

interface ExerciseLocalDataSource {

    fun addExercise(
        date: String,
        time: String,
        type: String,
        list: ExerciseSet,
        callbackLocal: ExerciseLocalDataSourceCallback.AddExerciseCallback
    )
}