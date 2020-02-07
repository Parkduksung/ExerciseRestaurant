package com.work.restaurant.view.calendar.presenter

import android.util.Log
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


    override fun getDataOfTheDayExerciseData(date: String) {

        exerciseRepository.getDataOfTheDay(
            date,
            object : ExerciseRepositoryCallback.GetDataOfTheDay {
                override fun onSuccess(list: List<ExerciseEntity>) {

                    val toExerciseModelList = list.map {
                        it.toExerciseModel()
                    }

                    calendarContract.showExerciseData(toExerciseModelList)

                }

                override fun onFailure(msg: String) {
                    Log.d("결과", msg)
                }
            })

    }

    override fun getDataOfTheDayEatData(date: String) {

        eatRepository.getDataOfTheDay(date, object : EatRepositoryCallback.GetDataOfTheDay {
            override fun onSuccess(list: List<EatEntity>) {
                val toEatModelList = list.map {
                    it.toEatModel()
                }

                calendarContract.showEatData(toEatModelList)
            }

            override fun onFailure(msg: String) {
                Log.d("결과", msg)
            }
        })


    }


}