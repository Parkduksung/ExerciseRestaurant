package com.work.restaurant.view.mypage.login.presenter

import com.work.restaurant.data.repository.login.LoginRepository
import com.work.restaurant.data.repository.login.LoginRepositoryCallback
import com.work.restaurant.data.repository.user.UserRepository
import com.work.restaurant.data.repository.user.UserRepositoryCallback

class MyPageLoginPresenter(
    private val myPageLoginView: MyPageLoginContract.View,
    private val userRepository: UserRepository,
    private val loginRepository: LoginRepository
) :
    MyPageLoginContract.Presenter {

    override fun changeState(userId: String,  userNickname: String) {

        loginRepository.changeState(userId, true, object : LoginRepositoryCallback.ChangeState {
            override fun onSuccess() {
                myPageLoginView.showLoginStateOk(userNickname)
            }
            override fun onFailure() {
                myPageLoginView.showLoginNo()
            }
        })
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


