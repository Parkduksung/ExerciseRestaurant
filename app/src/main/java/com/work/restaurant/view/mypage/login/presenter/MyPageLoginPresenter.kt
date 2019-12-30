package com.work.restaurant.view.mypage.login.presenter

import com.work.restaurant.data.repository.user.UserRepositoryCallback
import com.work.restaurant.data.repository.user.UserRepositoryImpl
import com.work.restaurant.data.source.remote.user.UserRemoteRemoteDataSourceSourceImpl
import com.work.restaurant.network.RetrofitInstance
import com.work.restaurant.view.mypage.main.MyPageFragment.Companion.URL

class MyPageLoginPresenter(private val myPageLoginView: MyPageLoginContract.View) :
    MyPageLoginContract.Presenter {
    override fun backPage() {
        myPageLoginView.showBackPage()
    }

    override fun registerPage() {
        myPageLoginView.showRegisterPage()
    }

    override fun findPass() {
        myPageLoginView.showFindPassPage()
    }

    override fun login(email: String, pass: String) {


        UserRepositoryImpl.getInstance(
            UserRemoteRemoteDataSourceSourceImpl.getInstance(
                RetrofitInstance.getInstance(URL)
            )
        )
            .login(email, pass, object : UserRepositoryCallback {
                override fun onSuccess(resultNickname: String) {
                    myPageLoginView.showLoginOk(resultNickname)
                }

                override fun onFailure(message: String) {
                    myPageLoginView.showLoginNo()
                }
            })


//        MyPageLoginRepositoryImpl.getInstance(
//            MyPageLoginDataImpl.getInstance()
//        ).loginResult(email, pass, object : MyPageLoginRepositoryCallback {
//
//            override fun onSuccess(resultNickname: String) {
//                myPageLoginView.showLoginOk(resultNickname)
//            }
//
//            override fun onFailure(message: String) {
//                myPageLoginView.showLoginNo()
//            }
//        })

    }


}