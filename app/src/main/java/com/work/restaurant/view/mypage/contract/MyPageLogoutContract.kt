package com.work.restaurant.view.mypage.contract

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