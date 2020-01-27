package com.work.restaurant.view.diary.add_exercise.presenter

import com.work.restaurant.data.model.ExerciseSet

interface AddExerciseContract {

    interface View {

        fun showAddResult(msg: String)

    }

    interface Presenter {

        fun addExercise(
            date: String,
            time: String,
            type: String,
            exerciseName: String,
            list: List<ExerciseSet>
        )
    }
}