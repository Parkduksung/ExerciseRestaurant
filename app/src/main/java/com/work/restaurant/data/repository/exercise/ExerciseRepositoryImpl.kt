package com.work.restaurant.data.repository.exercise

import com.work.restaurant.data.model.ExerciseSet
import com.work.restaurant.data.source.local.exercise.ExerciseLocalDataSource
import com.work.restaurant.db.room.entity.ExerciseEntity
import com.work.restaurant.db.room.entity.ExerciseSetResponse

class ExerciseRepositoryImpl(
    private val exerciseLocalDataSource: ExerciseLocalDataSource
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

        exerciseLocalDataSource.updateExercise(
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
        exerciseLocalDataSource.deleteEat(
            data,
            callback
        )
    }

    override fun getAllList(userId: String, callback: (getList: List<ExerciseEntity>) -> Unit) {
        exerciseLocalDataSource.getAllList(
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

        exerciseLocalDataSource.addExercise(
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
        exerciseLocalDataSource.getDataOfTheDay(
            userId,
            date,
            callback
        )
    }
}