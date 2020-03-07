package com.work.restaurant.view.diary.update_or_delete_exercise.presenter

import com.work.restaurant.data.model.ExerciseModel
import com.work.restaurant.data.model.ExerciseSet
import com.work.restaurant.data.repository.exercise.ExerciseRepository
import com.work.restaurant.data.repository.exercise.ExerciseRepositoryCallback

class UpdateOrDeleteExercisePresenter(
    private val updateOrDeleteView: UpdateOrDeleteExerciseContract.View,
    private val exerciseRepository: ExerciseRepository
) : UpdateOrDeleteExerciseContract.Presenter {
    override fun deleteExercise(exerciseModel: ExerciseModel) {

        val toExerciseEntity = exerciseModel.toExerciseEntity()

        exerciseRepository.deleteEat(
            toExerciseEntity,
            object : ExerciseRepositoryCallback.DeleteExerciseCallback {
                override fun onSuccess() {

                    updateOrDeleteView.showResult(1)
                }

                override fun onFailure() {
                    updateOrDeleteView.showResult(0)
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

        val toExerciseSetResponse = changeExerciseSet.map { it.toExerciseSetResponse() }

        exerciseRepository.updateExercise(
            changeTime,
            changeCategory,
            changeName,
            toExerciseSetResponse,
            exerciseModel.userId,
            exerciseModel.exerciseNum,
            object : ExerciseRepositoryCallback.UpdateExerciseCallback {
                override fun onSuccess() {
                    updateOrDeleteView.showResult(2)
                }

                override fun onFailure() {
                    updateOrDeleteView.showResult(0)
                }
            })


    }
}