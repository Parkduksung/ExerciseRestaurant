package com.work.restaurant.data.repository.exercise

import com.work.restaurant.data.model.ExerciseSet
import com.work.restaurant.network.room.entity.ExerciseEntity
import com.work.restaurant.network.room.entity.ExerciseSetResponse

interface ExerciseRepository {

    fun addExercise(
        userId: String,
        date: String,
        time: String,
        type: String,
        exerciseName: String,
        list: List<ExerciseSet>,
        callback: ExerciseRepositoryCallback.AddExerciseCallback
    )

    fun deleteEat(
        data: ExerciseEntity,
        callback: ExerciseRepositoryCallback.DeleteExerciseCallback
    )

    fun getList(
        userId: String,
        callback: ExerciseRepositoryCallback.GetAllList
    )

    fun getDataOfTheDay(
        userId: String,
        today: String,
        callback: ExerciseRepositoryCallback.GetDataOfTheDay
    )

    fun updateExercise(
        changeTime: String,
        changeType: String,
        changeExerciseName: String,
        changeExerciseSet: List<ExerciseSetResponse>,
        currentId: String,
        currentExerciseNum: Int,
        callback: ExerciseRepositoryCallback.UpdateExerciseCallback
    )

}