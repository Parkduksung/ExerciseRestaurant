package com.work.restaurant.view.diary.add_eat.presenter

interface AddEatContract {

    interface View {

        fun showAddResult(msg:String)
    }

    interface Presenter {

        fun addEat(date: String, time: String, type: Int, memo: String)
    }
}