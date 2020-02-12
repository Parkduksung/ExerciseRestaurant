package com.work.restaurant.view.mypage.find.presenter

interface MyPageFindPassContract {

    interface View {


        fun showResetOk(nickName: String)

        fun showResetNo(message: String)

    }

    interface Presenter {

        fun resetPass(email: String)


    }

}