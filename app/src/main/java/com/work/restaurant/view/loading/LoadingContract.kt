package com.work.restaurant.view.loading

interface LoadingContract {

    interface View{


        fun showStart()

        fun showDelay()

    }

    interface Presenter{

        fun randomText(loadingTextArrayList : Array<String>) : String

        fun delayTime() : Long

    }

}