package com.work.restaurant.data.source.local.exercise

import com.work.restaurant.data.model.ExerciseSet
import com.work.restaurant.network.room.database.ExerciseDatabase
import com.work.restaurant.network.room.entity.ExerciseEntity
import com.work.restaurant.network.room.entity.ExerciseSetResponse
import com.work.restaurant.util.AppExecutors

class ExerciseLocalDataSourceImpl(
    private val appExecutors: AppExecutors,
    private val exerciseDatabase: ExerciseDatabase
) : ExerciseLocalDataSource {
    override fun deleteEat(
        data: ExerciseEntity,
        callback: (isSuccess: Boolean) -> Unit
    ) {
        appExecutors.diskIO.execute {

            val deleteExercise =
                exerciseDatabase.exerciseDao().deleteExercise(data)

            appExecutors.mainThread.execute {
                if (deleteExercise >= 1) {
                    callback(true)
                } else {
                    callback(true)
                }
            }
        }
    }

    override fun updateExercise(
        changeTime: String,
        changeType: String,
        changeExerciseName: String,
        changeExerciseSet: List<ExerciseSetResponse>,
        currentId: String,
        currentExerciseNum: Int,
        callback: (isSuccess: Boolean) -> Unit
    ) {

        appExecutors.diskIO.execute {

            val updateExercise =
                exerciseDatabase.exerciseDao()
                    .updateEat(
                        changeTime,
                        changeType,
                        changeExerciseName,
                        changeExerciseSet,
                        currentId,
                        currentExerciseNum
                    )

            appExecutors.mainThread.execute {
                if (updateExercise >= 1) {
                    callback(true)
                } else {
                    callback(false)
                }
            }


        }

    }

    override fun getDataOfTheDay(
        userId: String,
        date: String,
        callback: (getList: List<ExerciseEntity>) -> Unit
    ) {
        appExecutors.diskIO.execute {

            val getDataOfTheDay =
                exerciseDatabase.exerciseDao().getTodayItem(userId, date)

            appExecutors.mainThread.execute {
                callback(getDataOfTheDay)
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
        callback: (isSuccess: Boolean) -> Unit
    ) {

        appExecutors.diskIO.execute {

            val exerciseSetResponseList =
                list.map {
                    it.toExerciseSetResponse()
                }

            val exerciseEntity =
                ExerciseEntity(
                    userId = userId,
                    date = date,
                    time = time,
                    type = type,
                    exerciseName = exerciseName,
                    exerciseSetList = exerciseSetResponseList
                )
            val registerExercise =
                exerciseDatabase.exerciseDao().registerExercise(exerciseEntity)


            appExecutors.mainThread.execute {
                if (registerExercise >= 1) {
                    callback(true)
                } else {
                    callback(false)
                }
            }
        }

    }

    override fun getAllList(userId: String, callback: (getList: List<ExerciseEntity>) -> Unit) {


        appExecutors.diskIO.execute {

            val getAllList =
                exerciseDatabase.exerciseDao().getAll(userId)

            appExecutors.mainThread.execute {
                callback(getAllList)
            }
        }

    }

    companion object {

        private var instance: ExerciseLocalDataSourceImpl? = null

        fun getInstance(
            appExecutors: AppExecutors,
            exerciseDatabase: ExerciseDatabase
        ): ExerciseLocalDataSourceImpl =
            instance ?: getInstance(
                appExecutors, exerciseDatabase
            ).also {
                instance = it
            }
    }

}