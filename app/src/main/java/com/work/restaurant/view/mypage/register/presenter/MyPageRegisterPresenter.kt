package com.work.restaurant.view.mypage.register.presenter

import com.work.restaurant.data.repository.login.LoginRepository
import com.work.restaurant.data.repository.login.LoginRepositoryCallback
import com.work.restaurant.data.repository.user.UserRepository
import com.work.restaurant.data.repository.user.UserRepositoryCallback
import java.util.regex.Pattern

class MyPageRegisterPresenter(
    private val myPageRegisterView: MyPageRegisterContract.View,
    private val userRepository: UserRepository,
    private val loginRepository: LoginRepository
) :
    MyPageRegisterContract.Presenter {
    override fun registerLogin(nickName: String, email: String, pass: String, state: Boolean) {

        loginRepository.getRegisterData(
            email,
            pass,
            nickName,
            state,
            object : LoginRepositoryCallback.RegisterCallback {
                override fun onSuccess() {
                    myPageRegisterView.showLoginState()
                }

                override fun onFailure() {
                    myPageRegisterView.showRegisterNo(1)
                }
            })

    }


    override fun isEmailValid(email: String): Boolean {
        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }


    override fun register(nickName: String, email: String, pass: String) {

        userRepository.register(nickName, email, pass, object : UserRepositoryCallback {
            override fun onSuccess(resultNickname: String) {
                myPageRegisterView.showRegisterState()
            }

            override fun onFailure(message: String) {
                myPageRegisterView.showRegisterNo(0)
            }
        })

    }

    override fun loginForRegister(userId: String, userPass: String) {

        userRepository.login(userId, userPass, object : UserRepositoryCallback {
            override fun onSuccess(resultNickname: String) {

                myPageRegisterView.showRegisterOk(resultNickname)
            }

            override fun onFailure(message: String) {
                myPageRegisterView.showRegisterNo(0)
            }
        })

    }

}