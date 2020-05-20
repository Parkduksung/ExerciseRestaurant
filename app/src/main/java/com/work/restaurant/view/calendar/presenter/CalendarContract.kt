package com.work.restaurant.view.calendar.presenter

import com.work.restaurant.data.model.EatModel
import com.work.restaurant.data.model.ExerciseModel

interface CalendarContract {

    interface View {

        fun showEatData(data: List<EatModel>)

        fun showExerciseData(data: List<ExerciseModel>)

        fun showAllDayIncludeEatData(list: Set<String>)

        fun showAllDayIncludeExerciseData(list: Set<String>)


    }

    interface Presenter {


        fun getDataOfTheDayExerciseData(userId: String, date: String)

        fun getDataOfTheDayEatData(userId: String, date: String)

        fun getAllEatData(userId: String)

        fun getAllExerciseData(userId: String)

    }

}