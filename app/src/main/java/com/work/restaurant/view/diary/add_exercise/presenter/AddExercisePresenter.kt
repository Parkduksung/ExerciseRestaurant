package com.work.restaurant.view.diary.add_exercise.presenter

import com.work.restaurant.data.model.ExerciseSet
import com.work.restaurant.data.repository.exercise.ExerciseRepository

class AddExercisePresenter(
    private val addExerciseView: AddExerciseContract.View,
    private val exerciseRepository: ExerciseRepository
) :
    AddExerciseContract.Presenter {

    override fun addExercise(
        userId: String,
        date: String,
        time: String,
        type: String,
        exerciseName: String,
        list: List<ExerciseSet>
    ) {
        exerciseRepository.addExercise(
            userId,
            date,
            time,
            type,
            exerciseName,
            list,
            callback = { add ->
                if (add) {
                    addExerciseView.showAddSuccess()
                }
            })

    }
}