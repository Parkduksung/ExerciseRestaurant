package com.work.restaurant.view.loading

interface LoadingContract {
    interface View {

        fun showStartText(text: String)

        fun showDelay()

        fun showLoginState(result: Boolean, userId: String, userNickname: String)
    }

    interface Presenter {

        fun randomText(loadingTextArrayList: Array<String>)

        fun delayTime()

        fun getAddressDataCount()

        fun registerAddress()

        fun getLoginState()

    }
}