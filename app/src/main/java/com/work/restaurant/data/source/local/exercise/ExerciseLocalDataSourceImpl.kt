package com.work.restaurant.data.source.local.exercise

import com.work.restaurant.data.model.ExerciseSet
import com.work.restaurant.network.room.database.ExerciseDatabase
import com.work.restaurant.network.room.entity.ExerciseEntity
import com.work.restaurant.util.AppExecutors

class ExerciseLocalDataSourceImpl(
    private val appExecutors: AppExecutors,
    private val exerciseDatabase: ExerciseDatabase
) : ExerciseLocalDataSource {

    override fun addExercise(
        date: String,
        time: String,
        type: String,
        list: ExerciseSet,
        callbackLocal: ExerciseLocalDataSourceCallback.AddExerciseCallback
    ) {

        appExecutors.diskIO.execute {

            val exerciseSetResponseList = list.toExerciseSetResponse()

            val exerciseEntity = ExerciseEntity(
                date = date,
                time = time,
                type = type,
                exerciseSetResponse = exerciseSetResponseList
            )

            val registerExercise = exerciseDatabase.exerciseDao().registerExercise(exerciseEntity)

            registerExercise.takeIf { true }
                .apply {
                    callbackLocal.onSuccess("success")
                } ?: callbackLocal.onFailure("error")
        }

    }


    companion object {

        private var instance: ExerciseLocalDataSourceImpl? = null

        fun getInstance(
            appExecutors: AppExecutors,
            eatDatabase: ExerciseDatabase
        ): ExerciseLocalDataSourceImpl =
            instance ?: ExerciseLocalDataSourceImpl(
                appExecutors,
                eatDatabase
            ).also {
                instance = it
            }
    }


}