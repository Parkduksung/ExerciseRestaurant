package com.work.restaurant.view.mypage.presenter

import com.work.restaurant.data.repository.mypage.UserRepositoryCallback
import com.work.restaurant.data.repository.mypage.UserRepositoryImpl
import com.work.restaurant.data.source.remote.UserRemoteDataSourceImpl
import com.work.restaurant.network.RetrofitInstance
import com.work.restaurant.view.mypage.contract.MyPageRegisterContract
import com.work.restaurant.view.mypage.fragment.MyPageLoginFragment.Companion.URL

class MyPageRegisterPresenter(private val myPageRegisterView: MyPageRegisterContract.View) :
    MyPageRegisterContract.Presenter {
    override fun backPage() {
        myPageRegisterView.showBackPage()
    }

    override fun register(nickName: String, email: String, pass: String) {

        UserRepositoryImpl.getInstance(UserRemoteDataSourceImpl.getInstance(RetrofitInstance.getInstance(URL)))
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