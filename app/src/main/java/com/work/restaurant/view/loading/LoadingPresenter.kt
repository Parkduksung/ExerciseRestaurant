package com.work.restaurant.view.loading

import android.util.Log
import com.work.restaurant.data.model.RoadModel
import com.work.restaurant.data.repository.login.LoginRepository
import com.work.restaurant.data.repository.login.LoginRepositoryCallback
import com.work.restaurant.data.repository.road.Callback
import com.work.restaurant.data.repository.road.RoadRepositoryDataCountCallback
import com.work.restaurant.data.repository.road.RoadRepositoryImpl
import com.work.restaurant.data.source.local.road.RoadLocalDataSourceImpl
import com.work.restaurant.network.room.database.AddressDatabase
import com.work.restaurant.network.room.entity.AddressEntity
import com.work.restaurant.network.room.entity.LoginEntity
import com.work.restaurant.util.App
import com.work.restaurant.util.AppExecutors
import kotlin.random.Random

class LoadingPresenter(
    private val loadingView: LoadingContract.View,
    private val loginRepository: LoginRepository
) : LoadingContract.Presenter {
    override fun registerAddress() {

        RoadRepositoryImpl.getInstance(
            RoadLocalDataSourceImpl.getInstance(
                AddressDatabase.getInstance(
                    App.instance.context()
                ),
                AppExecutors()
            )
        )
            .registerAddress(object : Callback {
                override fun onSuccess(list: List<AddressEntity>) {

                    val roadList = mutableListOf<RoadModel>()

                    list.forEach {

                        val roadModel =
                            RoadModel(it.si, it.gunGu, it.dong)
                        roadList.add(roadModel)
                    }

                    roadList.forEach {
                        it.toAddressEntity()
                    }

                }

                override fun onFailure(message: String) {
                    Log.d("결과결과", message)
                }
            })

    }

    override fun delayTime() =
        loadingView.showDelay()


    override fun randomText(loadingTextArrayList: Array<String>) {
        val random = Random.nextInt(loadingTextArrayList.size)
        loadingView.showStartText(loadingTextArrayList[random])
    }

    override fun getAddressDataCount() {

        RoadRepositoryImpl.getInstance(
            RoadLocalDataSourceImpl.getInstance(
                AddressDatabase.getInstance(
                    App.instance.context()
                ),
                AppExecutors()
            )
        )
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

    override fun getLoginState() {
        loginRepository.getLoginState(object : LoginRepositoryCallback.LoginStateCallback {
            override fun onSuccess(list: LoginEntity) {
                val getUserId = list.toLoginModel().loginId
                val getUserNickname = list.toLoginModel().loginNickname
                loadingView.showLoginState(true, getUserId, getUserNickname)
            }

            override fun onFailure() {
                loadingView.showLoginState(false, "", "")
            }
        })
    }
}