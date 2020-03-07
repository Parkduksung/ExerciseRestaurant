package com.work.restaurant.view.diary.update_or_delete_exercise.presenter

import com.work.restaurant.data.model.ExerciseModel
import com.work.restaurant.data.model.ExerciseSet

interface UpdateOrDeleteExerciseContract {

    interface View {
        fun showResult(sort: Int)
    }

    interface Presenter {

        fun deleteExercise(exerciseModel: ExerciseModel)

        fun updateExercise(
            changeTime: String,
            changeCategory: String,
            changeName: String,
            changeExerciseSet: List<ExerciseSet>,
            exerciseModel: ExerciseModel
        )

    }
}