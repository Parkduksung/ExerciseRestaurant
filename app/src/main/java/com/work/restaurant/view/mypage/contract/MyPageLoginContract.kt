package com.work.restaurant.view.mypage.contract

interface MyPageLoginContract {

    interface View {

        fun showLoginOk(nickName: String)

        fun showLoginNo()

        fun showLogout()

        fun showRegisterPage()

        fun showFindPassPage()

    }


    interface Presenter {

        fun login(email: String, pass: String)

        fun logout()

        fun registerPage()

        fun findPass()
    }

}