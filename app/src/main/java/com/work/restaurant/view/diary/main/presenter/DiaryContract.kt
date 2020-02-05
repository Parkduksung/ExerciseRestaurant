package com.work.restaurant.view.diary.main.presenter

import com.work.restaurant.data.model.DiaryModel
import com.work.restaurant.data.model.EatModel
import com.work.restaurant.data.model.ExerciseModel

interface DiaryContract {

    interface View {


        fun showEatData(data: List<EatModel>)

        fun showExerciseData(data: List<ExerciseModel>)

        fun showResult(msg: String)

    }

    interface Presenter {



        fun todayEatData(today: String)

        fun todayExerciseData(today: String)

        fun deleteEat(data: DiaryModel)

        fun deleteExercise(data: DiaryModel)

    }
}