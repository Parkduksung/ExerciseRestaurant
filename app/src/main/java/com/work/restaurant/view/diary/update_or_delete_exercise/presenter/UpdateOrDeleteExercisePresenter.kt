package com.work.restaurant.view.diary.update_or_delete_exercise.presenter

import com.work.restaurant.data.model.ExerciseModel
import com.work.restaurant.data.model.ExerciseSet
import com.work.restaurant.data.repository.exercise.ExerciseRepository

class UpdateOrDeleteExercisePresenter(
    private val updateOrDeleteView: UpdateOrDeleteExerciseContract.View,
    private val exerciseRepository: ExerciseRepository
) : UpdateOrDeleteExerciseContract.Presenter {
    override fun deleteExercise(exerciseModel: ExerciseModel) {

        val toExerciseEntity =
            exerciseModel.toExerciseEntity()

        exerciseRepository.deleteEat(
            toExerciseEntity,
            callback = { delete ->
                if (delete) {
                    updateOrDeleteView.showResult(SUCCESS_DELETE)
                } else {
                    updateOrDeleteView.showResult(FAIL_DELETE)
                }
            })

    }

    override fun updateExercise(
        changeTime: String,
        changeCategory: String,
        changeName: String,
        changeExerciseSet: List<ExerciseSet>,
        exerciseModel: ExerciseModel
    ) {

        val toExerciseSetResponse =
            changeExerciseSet.map { it.toExerciseSetResponse() }

        exerciseRepository.updateExercise(
            changeTime,
            changeCategory,
            changeName,
            toExerciseSetResponse,
            exerciseModel.userId,
            exerciseModel.exerciseNum,
            callback = { update ->
                if (update) {
                    updateOrDeleteView.showResult(SUCCESS_UPDATE)
                } else {
                    updateOrDeleteView.showResult(FAIL_UPDATE)
                }
            })
    }

    companion object {

        const val FAIL_UPDATE = 0
        const val FAIL_DELETE = 1
        const val SUCCESS_UPDATE = 2
        const val SUCCESS_DELETE = 3

    }
}