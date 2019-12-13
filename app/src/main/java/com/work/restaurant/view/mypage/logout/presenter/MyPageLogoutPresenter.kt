package com.work.restaurant.view.mypage.logout.presenter

class MyPageLogoutPresenter(private val myPageLogoutView: MyPageLogoutContract.View) :
    MyPageLogoutContract.Presenter {
    override fun logoutCancel() {
        myPageLogoutView.showLogoutCancel()
    }

    override fun logoutOk() {
        myPageLogoutView.showLogoutOk()
    }


}