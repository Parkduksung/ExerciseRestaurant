package com.work.restaurant.view.activity.loading

import kotlin.random.Random

class LoadingPresenter(private val loadingView: LoadingContract.View) : LoadingContract.Presenter {
    override fun delayTime() =
        loadingView.showDelay()


    override fun randomText(loadingTextArrayList: Array<String>) {

        val random = Random.nextInt(loadingTextArrayList.size)
        loadingView.showStartText(loadingTextArrayList[random])
    }

}