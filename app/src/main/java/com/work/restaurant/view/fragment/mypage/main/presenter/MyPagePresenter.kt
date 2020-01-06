package com.work.restaurant.view.fragment.mypage.main.presenter

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