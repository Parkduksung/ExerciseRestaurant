package com.work.restaurant.data.repository.exercise

import com.work.restaurant.network.room.entity.ExerciseEntity

interface ExerciseRepositoryCallback {


    interface AddExerciseCallback {
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