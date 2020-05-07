package com.work.restaurant.data.repository.exercise

import com.work.restaurant.data.model.ExerciseSet
import com.work.restaurant.data.source.local.exercise.ExerciseLocalDataSourceImpl
import com.work.restaurant.network.room.entity.ExerciseEntity
import com.work.restaurant.network.room.entity.ExerciseSetResponse

class ExerciseRepositoryImpl(
    private val exerciseLocalDataSourceImpl: ExerciseLocalDataSourceImpl
) : ExerciseRepository {
    override fun updateExercise(
        changeTime: String,
        changeType: String,
        changeExerciseName: String,
        changeExerciseSet: List<ExerciseSetResponse>,
        currentId: String,
        currentExerciseNum: Int,
        callback: (isSuccess: Boolean) -> Unit
    ) {

        exerciseLocalDataSourceImpl.updateExercise(
            changeTime,
            changeType,
            changeExerciseName,
            changeExerciseSet,
            currentId,
            currentExerciseNum,
            callback
        )

    }

    override fun deleteEat(
        data: ExerciseEntity,
        callback: (isSuccess: Boolean) -> Unit
    ) {
        exerciseLocalDataSourceImpl.deleteEat(
            data,
            callback
        )
    }

    override fun getAllList(userId: String, callback: (getList: List<ExerciseEntity>) -> Unit) {
        exerciseLocalDataSourceImpl.getAllList(
            userId,
            callback
        )
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

        exerciseLocalDataSourceImpl.addExercise(
            userId,
            date,
            time,
            type,
            exerciseName,
            list,
            callback
        )


    }

    override fun getDataOfTheDay(
        userId: String,
        date: String,
        callback: (getList: List<ExerciseEntity>) -> Unit
    ) {
        exerciseLocalDataSourceImpl.getDataOfTheDay(
            userId,
            date,
            callback
        )
    }

}