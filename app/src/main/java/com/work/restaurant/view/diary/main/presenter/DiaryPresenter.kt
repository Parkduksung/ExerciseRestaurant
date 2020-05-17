package com.work.restaurant.view.diary.main.presenter

import com.work.restaurant.data.repository.eat.EatRepository
import com.work.restaurant.data.repository.exercise.ExerciseRepository

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
            callback = { list ->
                if (list.isNotEmpty()) {
                    val getDataOfTheDayList =
                        list.map {
                            it.toExerciseModel()
                        }
                    diaryView.showExerciseData(getDataOfTheDayList.sortedBy { it.time })
                } else {
                    diaryView.showExerciseData(emptyList())
                }
            }
        )
    }

    override fun todayEatData(userId: String, today: String) {

        diaryView.showLoadingState(true)

        eatRepository.getDataOfTheDay(
            userId,
            today,
            callback = { list ->
                if (list.isNotEmpty()) {
                    val getDataOfTheDayList =
                        list.map {
                            it.toEatModel()
                        }
                    diaryView.showEatData(getDataOfTheDayList.sortedBy { it.time })
                } else {
                    diaryView.showEatData(emptyList())
                }
            })

    }


}


