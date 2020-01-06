package com.work.restaurant.view.activity

class ExerciseRestaurantPresenter(private val exerciseRestaurantView: ExerciseRestaurantContract.View) :
    ExerciseRestaurantContract.Presenter {

    override fun init() {
        exerciseRestaurantView.showInit()
    }


}



