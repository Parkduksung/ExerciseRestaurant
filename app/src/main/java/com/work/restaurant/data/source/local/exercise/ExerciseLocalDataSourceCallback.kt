package com.work.restaurant.data.source.local.exercise

interface ExerciseLocalDataSourceCallback {

    interface AddExerciseCallback {
        fun onSuccess(msg: String)
        fun onFailure(msg: String)
    }
}