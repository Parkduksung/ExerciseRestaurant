package com.work.restaurant.view.loading

import kotlin.random.Random

class LoadingPresenter  : LoadingContract.Presenter {
    override fun delayTime() =
        3000L


    override fun randomText(loadingTextArrayList: Array<String>): String {


        val random = Random.nextInt(loadingTextArrayList.size)

        return loadingTextArrayList[random]

    }

}