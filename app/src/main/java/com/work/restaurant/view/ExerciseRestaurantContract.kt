package com.work.restaurant.view

import androidx.fragment.app.Fragment

interface ExerciseRestaurantContract {

    interface View {
        fun showMain()
        fun showLoading()
    }


    interface Presenter {
        fun setFragmentMap(): Map<String, Fragment>
        fun start()
    }


}