package com.work.restaurant.view.mypage.withdraw.presenter

interface MyPageWithdrawalContract {

    interface View {

        fun showWithdrawOk(userNickname: String)

        fun showWithdrawLoginOk(userNickname: String)

        fun showProgressState(state: Boolean)

        fun showWithdrawNo()
    }

    interface Presenter {

        fun withdraw(userNickname: String, userEmail: String)

        fun withdrawLogin(userNickname: String, userEmail: String)

    }
}