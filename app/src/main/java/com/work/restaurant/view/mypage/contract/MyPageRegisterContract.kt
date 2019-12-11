package com.work.restaurant.view.mypage.contract

interface MyPageRegisterContract {

    interface View {

        fun showRegisterOk(nickName: String)

        fun showRegisterNo()

        fun showBackPage()

    }

    interface Presenter {

        fun register(nickName: String, email: String, pass: String)

        fun backPage()

    }

}