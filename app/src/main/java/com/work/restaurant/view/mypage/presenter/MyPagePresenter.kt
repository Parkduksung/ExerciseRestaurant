package com.work.restaurant.view.mypage.presenter

import com.work.restaurant.view.mypage.contract.MyPageContract

class MyPagePresenter(private val myPageView: MyPageContract.View) : MyPageContract.Presenter {
    override fun logIn() {
        myPageView.showLogIn()
    }

    override fun logOut() {
        myPageView.showLogOut()
    }

    override fun withDraw() {
        myPageView.showWithDraw()
    }

    override fun lateView() {
        myPageView.showLateView()
    }

}