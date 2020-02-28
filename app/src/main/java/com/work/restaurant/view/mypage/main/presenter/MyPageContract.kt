package com.work.restaurant.view.mypage.main.presenter

import com.work.restaurant.data.model.LoginModel

interface MyPageContract {

    interface View {

        fun showLoginState(model: LoginModel)

        fun showFirebaseLogin(loginId: String, loginNickname: String)

        fun showInit()

    }

    interface Presenter {

        fun getLoginState()

        fun loginFirebase(userId: String, userPass: String, userNickname: String)

    }


}