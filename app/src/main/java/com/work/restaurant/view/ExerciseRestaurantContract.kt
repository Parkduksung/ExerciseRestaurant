package com.work.restaurant.view

import androidx.fragment.app.Fragment

interface ExerciseRestaurantContract {

    interface View {
        fun showInit()
        fun showLoading()
    }


    interface Presenter {
        fun setFragmentMap(): Map<String, Fragment>
        fun init()
        fun loading()
    }


}