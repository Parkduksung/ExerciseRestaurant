package com.work.restaurant.view.mypage.logout.presenter

interface MyPageLogoutContract {

    interface View {

        fun showLogoutCancel()

        fun showLogoutOk()


    }

    interface Presenter {


        fun logoutCancel()


        fun logoutOk()

    }
}