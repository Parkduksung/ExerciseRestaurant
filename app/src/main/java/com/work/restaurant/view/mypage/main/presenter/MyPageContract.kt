package com.work.restaurant.view.mypage.main.presenter

interface MyPageContract {

    interface View {

        fun showMaintainLogin(email: String, nickname: String)

        fun showInit()

        fun showLoginOk(email: String, nickname: String)

        fun showLoginNo()

        fun showProgress()

        fun showEnd()

    }

    interface Presenter {

        fun getLoginState()

        fun login(email: String, pass: String)


    }


}