package com.work.restaurant.view.fragment.mypage.register_ok.presenter

class MyPageRegisterOkPresenter(private val myPageRegisterOkView: MyPageRegisterOkContract.View) :
    MyPageRegisterOkContract.Presenter {
    override fun registerOk() {
        myPageRegisterOkView.showRegisterOk()
    }
}