package com.work.restaurant.view.mypage.register.presenter

import com.work.restaurant.data.repository.login.LoginRepository
import com.work.restaurant.data.repository.login.LoginRepositoryCallback
import com.work.restaurant.data.repository.user.UserRepository
import com.work.restaurant.data.repository.user.UserRepositoryCallback

class MyPageRegisterPresenter(
    private val myPageRegisterView: MyPageRegisterContract.View,
    private val userRepository: UserRepository,
    private val loginRepository: LoginRepository
) :
    MyPageRegisterContract.Presenter {
    override fun emailDuplicationCheck(userId: String) {
        userRepository.emailDuplicationCheck(
            userId,
            object : UserRepositoryCallback.EmailDuplicationCheck {
                override fun onSuccess() {
                    myPageRegisterView.showEmailDuplicationCheck(true)
                }

                override fun onFailure() {
                    myPageRegisterView.showEmailDuplicationCheck(false)
                }
            })


    }

    private fun registerLogin(nickName: String, email: String, pass: String) {
        loginRepository.getRegisterData(
            email,
            pass,
            nickName,
            true,
            object : LoginRepositoryCallback.RegisterCallback {
                override fun onSuccess() {
                    myPageRegisterView.showRegisterOk()
                }

                override fun onFailure() {
                    myPageRegisterView.showRegisterNo(1)
                }
            })

    }


    override fun register(nickName: String, email: String, pass: String) {

        userRepository.register(nickName, email, pass, object : UserRepositoryCallback {
            override fun onSuccess(resultNickname: String) {
                registerLogin(nickName, email, pass)
            }

            override fun onFailure(message: String) {
                myPageRegisterView.showRegisterNo(0)
            }
        })

    }


}