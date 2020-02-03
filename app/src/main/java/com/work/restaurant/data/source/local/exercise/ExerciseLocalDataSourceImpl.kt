package com.work.restaurant.data.source.local.exercise

import com.work.restaurant.data.model.ExerciseSet
import com.work.restaurant.network.room.database.ExerciseDatabase
import com.work.restaurant.network.room.entity.ExerciseEntity
import com.work.restaurant.util.AppExecutors

class ExerciseLocalDataSourceImpl(
    private val appExecutors: AppExecutors,
    private val exerciseDatabase: ExerciseDatabase
) : ExerciseLocalDataSource {
    override fun deleteEat(
        data: ExerciseEntity,
        callback: ExerciseLocalDataSourceCallback.DeleteExerciseCallback
    ) {
        appExecutors.diskIO.execute {

            val deleteExercise = exerciseDatabase.exerciseDao().deleteExercise(data)

            deleteExercise.takeIf { true }
                .apply {
                    callback.onSuccess("success")
                } ?: callback.onFailure("error")

        }
    }

    override fun getDataOfTheDay(
        date: String,
        callback: ExerciseLocalDataSourceCallback.GetDataOfTheDay
    ) {
        appExecutors.diskIO.execute {


            val getDataOfTheDay = exerciseDatabase.exerciseDao().getTodayItem(date)

            getDataOfTheDay.takeIf { true }
                .apply {
                    callback.onSuccess(getDataOfTheDay)
                } ?: callback.onFailure("error")

        }
    }

    override fun addExercise(
        date: String,
        time: String,
        type: String,
        exerciseName: String,
        list: List<ExerciseSet>,
        callbackLocal: ExerciseLocalDataSourceCallback.AddExerciseCallback
    ) {

        appExecutors.diskIO.execute {


            val exerciseSetResponseList = list.map {
                it.toExerciseSetResponse()
            }

            val exerciseEntity = ExerciseEntity(
                date = date,
                time = time,
                type = type,
                exerciseName = exerciseName,
                exerciseSetList = exerciseSetResponseList
            )
            val registerExercise = exerciseDatabase.exerciseDao().registerExercise(exerciseEntity)

            registerExercise.takeIf { true }
                .apply {
                    callbackLocal.onSuccess("success")
                } ?: callbackLocal.onFailure("error")
        }

    }

    override fun getAllList(callback: ExerciseLocalDataSourceCallback.GetAllList) {


        appExecutors.diskIO.execute {

            val getAllList = exerciseDatabase.exerciseDao().getAll()



            getAllList.takeIf { true }
                .apply {
                    callback.onSuccess(getAllList)
                } ?: callback.onFailure("error")

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