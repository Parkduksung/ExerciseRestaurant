package com.work.restaurant.view.diary.add_exercise.presenter

import com.work.restaurant.data.model.ExerciseSet
import com.work.restaurant.data.repository.exercise.ExerciseRepository
import com.work.restaurant.data.repository.exercise.ExerciseRepositoryCallback

class AddExercisePresenter(
    private val addExerciseView: AddExerciseContract.View,
    private val exerciseRepository: ExerciseRepository
) :
    AddExerciseContract.Presenter {


    override fun addExercise(
        date: String,
        time: String,
        type: String,
        exerciseName: String,
        list: List<ExerciseSet>
    ) {
        exerciseRepository.addExercise(
            date,
            time,
            type,
            exerciseName,
            list,
            object : ExerciseRepositoryCallback.AddExerciseCallback {
                override fun onSuccess() {
                    addExerciseView.showAddSuccess()
                }

                override fun onFailure() {

                }
            })

    }
}