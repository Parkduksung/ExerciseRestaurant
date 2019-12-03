package com.work.restaurant.view.mypage.presenter

import com.work.restaurant.view.mypage.contract.MyPageRegisterOkContract

class MyPageRegisterOkPresenter(private val myPageRegisterOkView : MyPageRegisterOkContract.View) : MyPageRegisterOkContract.Presenter {
    override fun registerOk() {
        myPageRegisterOkView.showRegisterOk()
    }
}