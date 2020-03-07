package com.work.restaurant.data.source.local.exercise

import com.work.restaurant.network.room.entity.ExerciseEntity

interface ExerciseLocalDataSourceCallback {

    interface AddExerciseCallback {
        fun onSuccess()
        fun onFailure()
    }

    interface DeleteExerciseCallback {
        fun onSuccess()
        fun onFailure()

    }

    interface UpdateExerciseCallback {
        fun onSuccess()
        fun onFailure()

    }


    interface GetAllList {
        fun onSuccess(list: List<ExerciseEntity>)
        fun onFailure()

    }

    interface GetDataOfTheDay {

        fun onSuccess(list: List<ExerciseEntity>)
        fun onFailure()

    }

}