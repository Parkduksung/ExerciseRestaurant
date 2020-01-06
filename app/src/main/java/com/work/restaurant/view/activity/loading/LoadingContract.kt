package com.work.restaurant.view.activity.loading

interface LoadingContract {

    interface View {
        fun showStartText(text: String)
        fun showDelay()

    }
    interface Presenter {
        fun randomText(loadingTextArrayList: Array<String>)
        fun delayTime()
    }

}