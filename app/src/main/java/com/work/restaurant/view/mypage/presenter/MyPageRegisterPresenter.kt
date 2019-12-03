package com.work.restaurant.view.mypage.presenter

import com.work.restaurant.data.repository.mypage.MyPageRegisterRepositoryCallback
import com.work.restaurant.data.repository.mypage.MyPageRegisterRepositoryImpl
import com.work.restaurant.data.source.remote.MyPageRegisterDataImpl
import com.work.restaurant.view.mypage.contract.MyPageRegisterContract

class MyPageRegisterPresenter(private val myPageRegisterView: MyPageRegisterContract.View) :
    MyPageRegisterContract.Presenter {
    override fun backPage() {
        myPageRegisterView.showBackPage()
    }

    override fun register(nickName: String, email: String, pass: String) {

        MyPageRegisterRepositoryImpl.getInstance(MyPageRegisterDataImpl.getInstance())
            .getRegisterData(nickName, email, pass, object : MyPageRegisterRepositoryCallback {
                override fun onSuccess() {
                    myPageRegisterView.showRegisterOk()
                }

                override fun onFailure(message: String) {
                    myPageRegisterView.showRegisterNo()
                }
            })


    }
}