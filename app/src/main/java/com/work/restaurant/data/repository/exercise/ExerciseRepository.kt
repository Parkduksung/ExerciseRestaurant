package com.work.restaurant.data.repository.exercise

import com.work.restaurant.data.model.ExerciseSet
import com.work.restaurant.network.room.entity.ExerciseEntity

interface ExerciseRepository {

    fun addExercise(
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
        callback: ExerciseRepositoryCallback.GetAllList
    )

    fun getDataOfTheDay(
        today: String,
        callback: ExerciseRepositoryCallback.GetDataOfTheDay
    )

}