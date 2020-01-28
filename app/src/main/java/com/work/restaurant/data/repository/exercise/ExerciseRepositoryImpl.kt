package com.work.restaurant.data.repository.exercise

import com.work.restaurant.data.model.ExerciseSet
import com.work.restaurant.data.source.local.exercise.ExerciseLocalDataSourceCallback
import com.work.restaurant.data.source.local.exercise.ExerciseLocalDataSourceImpl
import com.work.restaurant.network.room.entity.ExerciseEntity

class ExerciseRepositoryImpl(
    private val exerciseLocalDataSourceImpl: ExerciseLocalDataSourceImpl
) : ExerciseRepository {
    override fun deleteEat(
        data: ExerciseEntity,
        callback: ExerciseRepositoryCallback.DeleteExerciseCallback
    ) {
        exerciseLocalDataSourceImpl.deleteEat(
            data,
            object : ExerciseLocalDataSourceCallback.DeleteExerciseCallback {
                override fun onSuccess(msg: String) {
                    callback.onSuccess(msg)
                }

                override fun onFailure(msg: String) {
                    callback.onFailure(msg)
                }
            })
    }

    override fun getList(callback: ExerciseRepositoryCallback.GetAllList) {
        exerciseLocalDataSourceImpl.getAllList(object : ExerciseLocalDataSourceCallback.GetAllList {
            override fun onSuccess(list: List<ExerciseEntity>) {
                callback.onSuccess(list)
            }

            override fun onFailure(msg: String) {
                callback.onFailure(msg)
            }
        })
    }

    override fun addExercise(
        date: String,
        time: String,
        type: String,
        exerciseName: String,
        list: List<ExerciseSet>,
        callback: ExerciseRepositoryCallback.AddExerciseCallback
    ) {

        exerciseLocalDataSourceImpl.addExercise(
            date,
            time,
            type,
            exerciseName,
            list,
            object : ExerciseLocalDataSourceCallback.AddExerciseCallback {

                override fun onSuccess(msg: String) {
                    callback.onSuccess(msg)
                }

                override fun onFailure(msg: String) {
                    callback.onFailure(msg)
                }
            })


    }

    override fun getDataOfTheDay(
        today: String,
        callback: ExerciseRepositoryCallback.GetDataOfTheDay
    ) {
        exerciseLocalDataSourceImpl.getDataOfTheDay(
            today,
            object : ExerciseLocalDataSourceCallback.GetDataOfTheDay {
                override fun onSuccess(list: List<ExerciseEntity>) {
                    callback.onSuccess(list)
                }

                override fun onFailure(msg: String) {
                    callback.onFailure(msg)
                }
            })
    }


    companion object {

        private var instance: ExerciseRepositoryImpl? = null

        fun getInstance(
            exerciseLocalDataSourceImpl: ExerciseLocalDataSourceImpl
        ): ExerciseRepository =
            instance ?: ExerciseRepositoryImpl(exerciseLocalDataSourceImpl)
                .also {
                    instance = it
                }

    }

}