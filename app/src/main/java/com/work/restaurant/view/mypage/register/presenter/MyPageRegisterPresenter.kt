package com.work.restaurant.view.mypage.register.presenter

import com.work.restaurant.data.repository.login.LoginRepository
import com.work.restaurant.data.repository.user.UserRepository

class MyPageRegisterPresenter(
    private val myPageRegisterView: MyPageRegisterContract.View,
    private val userRepository: UserRepository,
    private val loginRepository: LoginRepository
) :
    MyPageRegisterContract.Presenter {
    override fun emailDuplicationCheck(userId: String) {

        myPageRegisterView.showProgressState(true)

        userRepository.emailDuplicationCheck(
            userId,
            callback = { isSuccess ->
                if (isSuccess) {
                    myPageRegisterView.showEmailDuplicationCheck(true)
                } else {
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
            callback = { isSuccess ->
                if (isSuccess) {
                    myPageRegisterView.showRegisterOk()
                } else {
                    myPageRegisterView.showRegisterNo(NOT_VERIFIED_REGISTER)
                }
            })
    }


    override fun register(nickName: String, email: String, pass: String) {

        myPageRegisterView.showProgressState(true)

        userRepository.register(
            nickName,
            email,
            pass,
            callback = { resultNickname ->
                if (resultNickname != null) {
                    registerLogin(nickName, email, pass)
                } else {
                    myPageRegisterView.showRegisterNo(DUPLICATE_REGISTER)
                }
            })
    }

    companion object {
        const val DUPLICATE_REGISTER = 0
        const val NOT_VERIFIED_REGISTER = 1


    }


}