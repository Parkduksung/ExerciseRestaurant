package com.work.restaurant.view.mypage.find_ok.presenter

class MyPageFindOkPresenter(private val myPageFindOkView: MyPageFindOkContract.View) :
    MyPageFindOkContract.Presenter {
    override fun ok() {
        myPageFindOkView.showOk()
    }
}