package com.work.restaurant.data.repository.exercise

import com.work.restaurant.data.model.ExerciseSet

interface ExerciseRepository {

    fun addExercise(
        date: String,
        time: String,
        type: String,
        list: ExerciseSet,
        callback: ExerciseRepositoryCallback.AddExerciseCallback
    )

}