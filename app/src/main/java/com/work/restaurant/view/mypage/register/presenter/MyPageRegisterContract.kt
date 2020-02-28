package com.work.restaurant.view.mypage.register.presenter

interface MyPageRegisterContract {

    interface View {

        fun showRegisterOk(nickName: String)

        fun showRegisterState()

        fun showRegisterNo(sort: Int)

        fun showLoginState()

    }

    interface Presenter {

        fun register(nickName: String, email: String, pass: String)

        fun isEmailValid(email: String): Boolean

        fun registerLogin(nickName: String, email: String, pass: String, state: Boolean)

        fun loginForRegister(userId: String, userPass: String)

    }

}