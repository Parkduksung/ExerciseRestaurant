package com.work.restaurant.view.mypage.login.presenter

interface MyPageLoginContract {

    interface View {

        fun showLoginOk(nickName: String)

        fun showLoginStateOk(nickName: String)

        fun showLoginNo()

        fun showRegisterPage()

        fun showFindPassPage()

    }


    interface Presenter {

        fun login(email: String, pass: String)

        fun registerPage()

        fun findPass()

        fun changeState(userId: String,  userNickname:String)
    }

}