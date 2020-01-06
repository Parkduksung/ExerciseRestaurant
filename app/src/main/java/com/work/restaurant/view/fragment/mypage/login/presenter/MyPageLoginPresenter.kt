package com.work.restaurant.view.fragment.mypage.login.presenter

import com.work.restaurant.data.repository.user.UserRepository
import com.work.restaurant.data.repository.user.UserRepositoryCallback

class MyPageLoginPresenter(
    private val myPageLoginView: MyPageLoginContract.View,
    private val userRepository: UserRepository
) :
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


        userRepository.login(email, pass, object : UserRepositoryCallback {
            override fun onSuccess(resultNickname: String) {
                myPageLoginView.showLoginOk(resultNickname)
            }

            override fun onFailure(message: String) {
                myPageLoginView.showLoginNo()
            }
        })
    }

}


