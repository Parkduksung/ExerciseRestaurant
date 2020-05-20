package com.work.restaurant.view.loading

import com.work.restaurant.data.repository.login.LoginRepository
import com.work.restaurant.data.repository.road.RoadRepository
import kotlin.random.Random

class LoadingPresenter(
    private val loadingView: LoadingContract.View,
    private val loginRepository: LoginRepository,
    private val roadRepository: RoadRepository
) : LoadingContract.Presenter {
    override fun registerAddress() {
        roadRepository.registerAddress {
        }
    }

    override fun delayTime() =
        loadingView.showDelay()


    override fun randomText(loadingTextArrayList: Array<String>) {
        val random =
            Random.nextInt(loadingTextArrayList.size)

        loadingView.showStartText(loadingTextArrayList[random])
    }

    override fun getAddressDataCount() {
    }

    override fun getLoginState() {
        loginRepository.getLoginState(
            callback = { list ->
                if (list != null) {
                    val getUserId = list.toLoginModel().loginId
                    val getUserNickname = list.toLoginModel().loginNickname
                    loadingView.showLoginState(true, getUserId, getUserNickname)
                } else {
                    loadingView.showLoginState(false, EMPTY_TEXT, EMPTY_TEXT)
                }
            })
    }


    companion object {

        private const val EMPTY_TEXT = ""

    }
}