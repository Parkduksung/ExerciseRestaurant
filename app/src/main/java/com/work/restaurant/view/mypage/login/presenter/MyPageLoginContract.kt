package com.work.restaurant.view.mypage.login.presenter

interface MyPageLoginContract {

    interface View {

        fun showLoginOk(nickName: String)

        fun showLoginNo()

        fun showBackPage()

        fun showRegisterPage()

        fun showFindPassPage()

    }


    interface Presenter {

        fun login(email: String, pass: String)

        fun backPage()

        fun registerPage()

        fun findPass()
    }

}