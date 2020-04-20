package com.work.restaurant.view.diary.main.presenter

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

    override fun todayExerciseData(userId: String, today: String) {

        diaryView.showLoadingState(true)

        exerciseRepository.getDataOfTheDay(
            userId,
            today,
            object : ExerciseRepositoryCallback.GetDataOfTheDay {
                override fun onSuccess(list: List<ExerciseEntity>) {

                    val getDataOfTheDayList = list.map {
                        it.toExerciseModel()
                    }
                    diaryView.showExerciseData(getDataOfTheDayList.sortedBy { it.time })
                }

                override fun onFailure() {

                }
            })
    }

    override fun todayEatData(userId: String, today: String) {

        diaryView.showLoadingState(true)

        eatRepository.getDataOfTheDay(
            userId,
            today,
            object : EatRepositoryCallback.GetDataOfTheDay {
                override fun onSuccess(list: List<EatEntity>) {
                    val getDataOfTheDayList = list.map {
                        it.toEatModel()
                    }
                    diaryView.showEatData(getDataOfTheDayList.sortedBy { it.time })
                }

                override fun onFailure() {

                }
            })

    }


}


