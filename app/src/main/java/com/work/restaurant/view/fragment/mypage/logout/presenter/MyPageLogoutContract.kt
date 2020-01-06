package com.work.restaurant.view.fragment.mypage.logout.presenter

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