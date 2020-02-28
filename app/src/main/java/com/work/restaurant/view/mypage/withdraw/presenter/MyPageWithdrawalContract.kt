package com.work.restaurant.view.mypage.withdraw.presenter

interface MyPageWithdrawalContract {

    interface View{

        fun showWithdrawCancel()

        fun showWithdrawOk(userNickname: String)

        fun showWithdrawLoginOk(userNickname: String)

        fun showWithdrawNo(sort : Int)
    }

    interface Presenter{

        fun withdrawCancel()

        fun withdraw(userNickname: String, userEmail: String)

        fun withdrawLogin(userNickname: String, userEmail: String)

    }
}