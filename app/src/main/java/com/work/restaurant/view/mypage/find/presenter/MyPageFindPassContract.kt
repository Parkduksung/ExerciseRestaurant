package com.work.restaurant.view.mypage.find.presenter

interface MyPageFindPassContract {

    interface View {


        fun showResetOk(nickName: String)

        fun showResetNo(message: String)

        fun showBackPage()

    }

    interface Presenter {

        fun resetPass(email: String)

        fun backPage()
    }

}