package com.work.restaurant.view.mypage.withdraw.presenter

interface MyPageWithdrawalContract {

    interface View{

        fun showWithdrawCancel()

        fun showWithdrawOk(userNickname: String)

        fun showWithdrawNo()
    }

    interface Presenter{

        fun withdrawCancel()

        fun withdraw(userNickname: String)

    }
}