package com.work.restaurant.view.diary.main.presenter

import com.work.restaurant.data.model.EatModel

interface DiaryContract {

    interface View {
        fun showData(data: List<EatModel>)
    }

    interface Presenter {

        fun data()

        fun todayData(today: String)

    }
}