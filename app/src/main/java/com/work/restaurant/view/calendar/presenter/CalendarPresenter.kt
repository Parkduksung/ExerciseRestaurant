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


    override fun getDataOfTheDay(date: String) {

        eatRepository.getDataOfTheDay(date, object : EatRepositoryCallback.GetDataOfTheDay {
            override fun onSuccess(list: List<EatEntity>) {
                calendarContract.showDataOfTheDay(list.map { it.toEatModel().toDiaryModel() })
            }

            override fun onFailure(msg: String) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

        exerciseRepository.getDataOfTheDay(date,
            object : ExerciseRepositoryCallback.GetDataOfTheDay {
                override fun onSuccess(list: List<ExerciseEntity>) {
                    calendarContract.showDataOfTheDay(list.map {
                        it.toExerciseModel().toDiaryModel()
                    })
                }

                override fun onFailure(msg: String) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            })

    }
}