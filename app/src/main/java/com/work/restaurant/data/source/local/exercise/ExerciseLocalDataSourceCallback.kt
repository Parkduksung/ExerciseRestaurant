package com.work.restaurant.data.source.local.exercise

import com.work.restaurant.network.room.entity.ExerciseEntity

interface ExerciseLocalDataSourceCallback {

    interface AddExerciseCallback {
        fun onSuccess(msg: String)
        fun onFailure(msg: String)
    }

    interface DeleteExerciseCallback {

        fun onSuccess(msg: String)
        fun onFailure(msg: String)

    }


    interface GetAllList {

        fun onSuccess(list: List<ExerciseEntity>)
        fun onFailure(msg: String)

    }

    interface GetDataOfTheDay {

        fun onSuccess(list: List<ExerciseEntity>)
        fun onFailure(msg: String)

    }

}