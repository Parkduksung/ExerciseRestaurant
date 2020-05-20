package com.work.restaurant.view.mypage.logout.presenter

interface MyPageLogoutContract {

    interface View {

        fun showLogoutOk()

        fun showLogoutNo()

        fun showProgressState(state: Boolean)
    }

    interface Presenter {

        fun logoutOk(userId: String)

    }
}