package com.work.restaurant.view.calendar.presenter

import com.work.restaurant.data.repository.eat.EatRepository
import com.work.restaurant.data.repository.eat.EatRepositoryCallback
import com.work.restaurant.data.repository.exercise.ExerciseRepository
import com.work.restaurant.data.repository.exercise.ExerciseRepositoryCallback
import com.work.restaurant.network.room.entity.EatEntity
import com.work.restaurant.network.room.entity.ExerciseEntity

class CalendarPresenter(
    private val calendarContract: CalendarContract.View,
    private val eatRepository: EatRepository,
    private val exerciseRepository: ExerciseRepository
) : CalendarContract.Presenter {

    override fun getAllEatData(userId: String) {

        eatRepository.getList(userId, object : EatRepositoryCallback.GetAllList {
            override fun onSuccess(list: List<EatEntity>) {

                val toEatModel = list.map { it.toEatModel() }

                val toOnlyDateFromEatModelList = toEatModel.map { it.date }.toSet()

                calendarContract.showAllDayIncludeEatData(toOnlyDateFromEatModelList)

            }

            override fun onFailure() {

            }
        })

    }

    override fun getAllExerciseData(userId: String) {

        exerciseRepository.getList(userId, object : ExerciseRepositoryCallback.GetAllList {
            override fun onSuccess(list: List<ExerciseEntity>) {

                val toExerciseModel = list.map { it.toExerciseModel() }

                val toOnlyDateFromExerciseModelList = toExerciseModel.map { it.date }.toSet()

                calendarContract.showAllDayIncludeExerciseData(toOnlyDateFromExerciseModelList)

            }

            override fun onFailure() {

            }
        })

    }


    override fun getDataOfTheDayExerciseData(userId: String, date: String) {

        exerciseRepository.getDataOfTheDay(
            userId,
            date,
            object : ExerciseRepositoryCallback.GetDataOfTheDay {
                override fun onSuccess(list: List<ExerciseEntity>) {
                    val toExerciseModelList = list.map {
                        it.toExerciseModel()
                    }
                    calendarContract.showExerciseData(toExerciseModelList)
                }

                override fun onFailure() {

                }
            })

    }

    override fun getDataOfTheDayEatData(userId: String, date: String) {

        eatRepository.getDataOfTheDay(
            userId,
            date,
            object : EatRepositoryCallback.GetDataOfTheDay {
                override fun onSuccess(list: List<EatEntity>) {
                    val toEatModelList = list.map {
                        it.toEatModel()
                    }

                    calendarContract.showEatData(toEatModelList)
                }

                override fun onFailure() {

                }
            })

    }


}