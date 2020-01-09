package com.work.restaurant.view.mypage.main.presenter

interface MyPageContract {

    interface View {

        fun showLogIn()

        fun showLogOut()

        fun showWithDraw()

        fun showLateView()

    }

    interface Presenter {

        fun logIn()

        fun logOut()

        fun withDraw()

        fun lateView()
    }


}