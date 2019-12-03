package com.work.restaurant.view.mypage.contract

interface MyPageWithdrawalContract {

    interface View{

        fun showWithdrawCancel()

        fun showWithdrawOk()

        fun showWithdrawNo()
    }

    interface Presenter{

        fun withdrawCancel()

        fun withdraw(userNickname: String)

    }
}