package com.work.restaurant.view.mypage.presenter

import com.work.restaurant.view.mypage.contract.MyPageLogoutContract

class MyPageLogoutPresenter(private val myPageLogoutView: MyPageLogoutContract.View) :
    MyPageLogoutContract.Presenter {
    override fun logoutCancel() {
        myPageLogoutView.showLogoutCancel()
    }

    override fun logoutOk() {
        myPageLogoutView.showLogoutOk()
    }


}