package com.work.restaurant.view.mypage.presenter

import com.work.restaurant.data.repository.mypage.MyPageWithdrawRepositoryCallback
import com.work.restaurant.data.repository.mypage.MyPageWithdrawRepositoryImpl
import com.work.restaurant.data.source.remote.MyPageWithdrawDataImpl
import com.work.restaurant.view.mypage.contract.MyPageWithdrawalContract

class MyPageWithdrawalPresenter(private val myPageWithdrawalView: MyPageWithdrawalContract.View) :
    MyPageWithdrawalContract.Presenter {

    override fun withdrawCancel() {
        myPageWithdrawalView.showWithdrawCancel()
    }

    override fun withdraw(userNickname: String) {
        MyPageWithdrawRepositoryImpl.getInstance(
            MyPageWithdrawDataImpl.getInstance()
        ).withdrawResult(userNickname, object : MyPageWithdrawRepositoryCallback {
            override fun onSuccess() {
                myPageWithdrawalView.showWithdrawOk()
            }

            override fun onFailure(message: String) {
                myPageWithdrawalView.showWithdrawNo()
            }
        })


    }

}