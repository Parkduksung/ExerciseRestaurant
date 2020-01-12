package com.work.restaurant.view.loading

import android.util.Log
import com.work.restaurant.data.repository.road.RoadRepositoryDataCountCallback
import com.work.restaurant.data.repository.road.RoadRepositoryImpl
import com.work.restaurant.data.source.local.road.RoadLocalDataSourceImpl
import kotlin.random.Random

class LoadingPresenter(private val loadingView: LoadingContract.View) : LoadingContract.Presenter {
    override fun registerAddress() {
        //등록하는곳
    }

    override fun delayTime() =
        loadingView.showDelay()


    override fun randomText(loadingTextArrayList: Array<String>) {

        val random = Random.nextInt(loadingTextArrayList.size)
        loadingView.showStartText(loadingTextArrayList[random])
    }

    override fun getAddressDataCount() {

        RoadRepositoryImpl.getInstance(RoadLocalDataSourceImpl.getInstance())
            .getAddressCount(object : RoadRepositoryDataCountCallback {
                override fun onSuccess(state: Boolean) {
                    if (state) {
                        //있으면 db에 등록 ㄴㄴ
                        Log.d("결과결과", "갯수있음")
                    } else {
                        //없으면 db에 등록하는 함수 ㄱㄱ
                        registerAddress() // 구성하면됨.
                        Log.d("결과결과", "갯수없음")
                    }
                }
                override fun onFailure(message: String) {
                    Log.d("결과결과", message)
                }

            })
    }

}