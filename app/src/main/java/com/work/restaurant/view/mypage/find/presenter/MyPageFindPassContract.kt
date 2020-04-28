package com.work.restaurant.view.mypage.find.presenter

interface MyPageFindPassContract {

    interface View {

        fun showResetOk()

        fun showResetNo()

        fun showProgressState(state: Boolean)

    }

    interface Presenter {

        fun resetPass(email: String)


    }

}