package com.work.restaurant.data.repository.exercise

import com.work.restaurant.data.model.ExerciseSet
import com.work.restaurant.data.source.local.exercise.ExerciseLocalDataSourceCallback
import com.work.restaurant.data.source.local.exercise.ExerciseLocalDataSourceImpl

class ExerciseRepositoryImpl(
    private val exerciseLocalDataSourceImpl: ExerciseLocalDataSourceImpl
) : ExerciseRepository {

    override fun addExercise(
        date: String,
        time: String,
        type: String,
        list: ExerciseSet,
        callback: ExerciseRepositoryCallback.AddExerciseCallback
    ) {

        exerciseLocalDataSourceImpl.addExercise(
            date,
            time,
            type,
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