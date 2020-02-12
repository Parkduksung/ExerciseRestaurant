package com.work.restaurant.view.mypage.register.presenter

interface MyPageRegisterContract {

    interface View {

        fun showRegisterOk(nickName: String)

        fun showRegisterNo()

    }

    interface Presenter {

        fun register(nickName: String, email: String, pass: String)

        fun isEmailValid(email: String): Boolean


    }

}