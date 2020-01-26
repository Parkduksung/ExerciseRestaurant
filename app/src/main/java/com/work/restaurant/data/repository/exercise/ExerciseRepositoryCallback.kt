package com.work.restaurant.data.repository.exercise

interface ExerciseRepositoryCallback {


    interface AddExerciseCallback {
        fun onSuccess(msg: String)
        fun onFailure(msg: String)
    }

}