package com.work.restaurant.view.diary.main.presenter

import com.work.restaurant.data.model.DateModel

interface DiaryContract {

    interface View {
        fun showData(data : List<DateModel>)
    }

    interface Presenter {

        fun data()

    }
}