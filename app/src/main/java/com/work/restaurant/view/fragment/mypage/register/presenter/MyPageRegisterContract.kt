package com.work.restaurant.view.fragment.mypage.register.presenter

interface MyPageRegisterContract {

    interface View {

        fun showRegisterOk(nickName: String)

        fun showRegisterNo()

        fun showBackPage()

    }

    interface Presenter {

        fun register(nickName: String, email: String, pass: String)

        fun backPage()

        fun isEmailValid(email: String): Boolean


    }

}