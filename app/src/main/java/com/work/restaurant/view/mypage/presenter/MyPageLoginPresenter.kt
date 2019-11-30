package com.work.restaurant.view.mypage.presenter

import com.work.restaurant.data.repository.mypage.MyPageLoginRepositoryCallback
import com.work.restaurant.data.repository.mypage.MyPageLoginRepositoryImpl
import com.work.restaurant.data.source.remote.MyPageLoginDataImpl
import com.work.restaurant.view.mypage.contract.MyPageLoginContract

class MyPageLoginPresenter(private val myPageLoginView: MyPageLoginContract.View) :
    MyPageLoginContract.Presenter {
    override fun logout() {
        myPageLoginView.showLogout()
    }

    override fun registerPage() {
        myPageLoginView.showRegisterPage()
    }

    override fun findPass() {
        myPageLoginView.showFindPassPage()
    }

    override fun login(email: String, pass: String) {


        MyPageLoginRepositoryImpl.getInstance(
            MyPageLoginDataImpl.getInstance()
        ).loginResult(email, pass, object : MyPageLoginRepositoryCallback {

            override fun onSuccess(resultNickname: String) {

                myPageLoginView.showLoginOk(resultNickname)
            }

            override fun onFailure(message: String) {
                myPageLoginView.showLoginNo()
            }
        })

    }


}