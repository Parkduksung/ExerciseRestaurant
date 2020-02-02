package com.work.restaurant.view.calendar.presenter

import com.work.restaurant.data.model.EatModel
import com.work.restaurant.data.model.ExerciseModel

interface CalendarContract {

    interface View {

        fun showEatData(data: List<EatModel>)

        fun showExerciseData(data: List<ExerciseModel>)


    }

    interface Presenter {


        fun getDataOfTheDayExerciseData(date: String)

        fun getDataOfTheDayEatData(date: String)

    }

}