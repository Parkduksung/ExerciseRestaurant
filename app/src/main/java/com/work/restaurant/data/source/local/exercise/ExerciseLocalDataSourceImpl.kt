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

            appExecutors.mainThread.execute {
                if (deleteExercise >= 1) {
                    callback.onSuccess()
                } else {
                    callback.onSuccess()
                }
            }
        }
    }

    override fun getDataOfTheDay(
        userId: String,
        date: String,
        callback: ExerciseLocalDataSourceCallback.GetDataOfTheDay
    ) {
        appExecutors.diskIO.execute {

            val getDataOfTheDay = exerciseDatabase.exerciseDao().getTodayItem(userId, date)

            appExecutors.mainThread.execute {
                getDataOfTheDay.takeIf { true }
                    .apply {
                        callback.onSuccess(getDataOfTheDay)
                    } ?: callback.onFailure()

            }

        }
    }

    override fun addExercise(
        userId: String,
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
                userId = userId,
                date = date,
                time = time,
                type = type,
                exerciseName = exerciseName,
                exerciseSetList = exerciseSetResponseList
            )
            val registerExercise = exerciseDatabase.exerciseDao().registerExercise(exerciseEntity)


            appExecutors.mainThread.execute {
                if (registerExercise >= 1) {
                    callbackLocal.onSuccess()
                } else {
                    callbackLocal.onFailure()
                }
            }
        }

    }

    override fun getAllList(userId: String, callback: ExerciseLocalDataSourceCallback.GetAllList) {


        appExecutors.diskIO.execute {

            val getAllList = exerciseDatabase.exerciseDao().getAll(userId)

            appExecutors.mainThread.execute {
                getAllList.takeIf { true }
                    .apply {
                        callback.onSuccess(getAllList)
                    } ?: callback.onFailure()
            }
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