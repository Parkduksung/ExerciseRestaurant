package com.work.restaurant.view.calendar.presenter

import com.work.restaurant.data.repository.eat.EatRepository
import com.work.restaurant.data.repository.exercise.ExerciseRepository

class CalendarPresenter(
    private val calendarContract: CalendarContract.View,
    private val eatRepository: EatRepository,
    private val exerciseRepository: ExerciseRepository
) : CalendarContract.Presenter {

    override fun getAllEatData(userId: String) {

        eatRepository.getList(
            userId,
            callback = { list ->

                if (list.isNotEmpty()) {

                    val toEatModel =
                        list.map { it.toEatModel() }

                    val toOnlyDateFromEatModelList =
                        toEatModel.map { it.date }.toSet()

                    calendarContract.showAllDayIncludeEatData(toOnlyDateFromEatModelList)
                } else {
                    calendarContract.showAllDayIncludeExerciseData(emptySet())
                }
            })

    }

    override fun getAllExerciseData(userId: String) {

        exerciseRepository.getAllList(
            userId,
            callback = { list ->
                if (list.isNotEmpty()) {
                    val toExerciseModel =
                        list.map { it.toExerciseModel() }

                    val toOnlyDateFromExerciseModelList =
                        toExerciseModel.map { it.date }.toSet()

                    calendarContract.showAllDayIncludeExerciseData(toOnlyDateFromExerciseModelList)
                } else {
                    calendarContract.showAllDayIncludeExerciseData(emptySet())
                }
            })

    }


    override fun getDataOfTheDayExerciseData(userId: String, date: String) {

        exerciseRepository.getDataOfTheDay(
            userId,
            date,
            callback = { list ->
                if (list.isNotEmpty()) {
                    val toExerciseModelList = list.map {
                        it.toExerciseModel()
                    }
                    calendarContract.showExerciseData(toExerciseModelList)
                } else {
                    calendarContract.showExerciseData(emptyList())
                }
            })

    }

    override fun getDataOfTheDayEatData(userId: String, date: String) {

        eatRepository.getDataOfTheDay(
            userId,
            date,
            callback = { list ->
                if (list.isNotEmpty()) {
                    val toEatModelList =
                        list.map {
                            it.toEatModel()
                        }
                    calendarContract.showEatData(toEatModelList)
                } else {
                    calendarContract.showEatData(emptyList())
                }
            }
        )

    }


}