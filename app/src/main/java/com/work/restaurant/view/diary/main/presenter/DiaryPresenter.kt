package com.work.restaurant.view.diary.main.presenter

import com.work.restaurant.data.model.DiaryModel
import com.work.restaurant.data.repository.eat.EatRepository
import com.work.restaurant.data.repository.eat.EatRepositoryCallback
import com.work.restaurant.data.repository.exercise.ExerciseRepository
import com.work.restaurant.data.repository.exercise.ExerciseRepositoryCallback
import com.work.restaurant.network.room.entity.EatEntity
import com.work.restaurant.network.room.entity.ExerciseEntity

class DiaryPresenter(
    private val diaryView: DiaryContract.View,
    private val eatRepository: EatRepository,
    private val exerciseRepository: ExerciseRepository
) : DiaryContract.Presenter {
    override fun deleteExercise(data: DiaryModel) {

        val toExerciseEntity = data.toExerciseEntity()

        exerciseRepository.deleteEat(
            toExerciseEntity,
            object : ExerciseRepositoryCallback.DeleteExerciseCallback {
                override fun onSuccess(msg: String) {
                    diaryView.showResult(msg)

                }

                override fun onFailure(msg: String) {
                    diaryView.showResult(msg)
                }
            })
    }

    override fun deleteEat(data: DiaryModel) {

        val toEatEntity = data.toEatEntity()

        eatRepository.deleteEat(toEatEntity, object : EatRepositoryCallback.DeleteEatCallback {
            override fun onSuccess(msg: String) {
                diaryView.showResult(msg)
            }

            override fun onFailure(msg: String) {
                diaryView.showResult(msg)
            }
        })
    }

    override fun todayExerciseData(today: String) {

        exerciseRepository.getDataOfTheDay(
            today,
            object : ExerciseRepositoryCallback.GetDataOfTheDay {
                override fun onSuccess(list: List<ExerciseEntity>) {


                    val getDataOfTheDayList = list.map {
                        it.toExerciseModel()
                    }

                    diaryView.showExerciseData(getDataOfTheDayList.sortedBy { it.time })

                }

                override fun onFailure(msg: String) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            })
    }


    override fun todayEatData(today: String) {

        eatRepository.getDataOfTheDay(today, object : EatRepositoryCallback.GetDataOfTheDay {
            override fun onSuccess(list: List<EatEntity>) {
                val getDataOfTheDayList = list.map {
                    it.toEatModel()
                }

                diaryView.showEatData(getDataOfTheDayList.sortedBy { it.time })

            }

            override fun onFailure(msg: String) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })


    }


}

