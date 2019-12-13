package com.work.restaurant.view.mypage.register.presenter

import com.work.restaurant.data.repository.user.UserRepositoryCallback
import com.work.restaurant.data.repository.user.UserRepositoryImpl
import com.work.restaurant.data.source.remote.user.UserRemoteRemoteDataSourceSourceImpl
import com.work.restaurant.network.RetrofitInstance
import com.work.restaurant.view.mypage.main.MyPageFragment.Companion.URL

class MyPageRegisterPresenter(private val myPageRegisterView: MyPageRegisterContract.View) :
    MyPageRegisterContract.Presenter {
    override fun backPage() {
        myPageRegisterView.showBackPage()
    }

    override fun register(nickName: String, email: String, pass: String) {

        UserRepositoryImpl.getInstance(
            UserRemoteRemoteDataSourceSourceImpl.getInstance(
                RetrofitInstance.getInstance(URL)
            )
        )
            .register(nickName, email, pass, object : UserRepositoryCallback {
                override fun onSuccess(resultNickname: String) {
                    myPageRegisterView.showRegisterOk(resultNickname)
                }

                override fun onFailure(message: String) {
                    myPageRegisterView.showRegisterNo()
                }
            })

    }
}