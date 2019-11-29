package com.work.restaurant.view.mypage.contract

interface MyPageContract {

    interface View{


        fun logIn()

        fun logOut()

        fun withDrawal()

        fun lateView()

        fun showLoginState()

    }

    interface Presenter{


        fun loginState()


    }




}