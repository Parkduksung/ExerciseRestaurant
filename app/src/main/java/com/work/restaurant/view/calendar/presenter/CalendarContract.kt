package com.work.restaurant.view.calendar.presenter

import com.work.restaurant.data.model.DiaryModel

interface CalendarContract {

    interface View {
        fun showDataOfTheDay(list: List<DiaryModel>)
    }

    interface Presenter {

        fun getDataOfTheDay(date: String)

    }

}