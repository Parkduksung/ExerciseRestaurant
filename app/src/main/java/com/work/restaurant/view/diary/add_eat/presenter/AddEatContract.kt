package com.work.restaurant.view.diary.add_eat.presenter

interface AddEatContract {

    interface View {
        fun showAddSuccess()
    }

    interface Presenter {

        fun addEat(date: String, time: String, type: Int, memo: String)
    }
}