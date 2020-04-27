package com.work.restaurant.view.mypage.main.presenter

import com.work.restaurant.data.repository.login.LoginRepository
import com.work.restaurant.data.repository.login.LoginRepositoryCallback
import com.work.restaurant.data.repository.user.UserRepository
import com.work.restaurant.data.repository.user.UserRepositoryCallback
import com.work.restaurant.network.room.entity.LoginEntity
import com.work.restaurant.util.RelateLogin

class MyPagePresenter(
    private val myPageView: MyPageContract.View,
    private val loginRepository: LoginRepository,
    private val userRepository: UserRepository
) : MyPageContract.Presenter {


    override fun login(email: String, pass: String) {
        userRepository.login(email, pass, object : UserRepositoryCallback {
            override fun onSuccess(resultNickname: String) {
                checkRegister(email, pass, resultNickname)
            }

            override fun onFailure(message: String) {
                myPageView.showLoginNo()
            }
        })
    }

    override fun getLoginState() {
        loginRepository.getLoginState(object : LoginRepositoryCallback.LoginStateCallback {
            override fun onSuccess(list: LoginEntity) {
                val toLoginModel =
                    list.toLoginModel()
                autoLogin(toLoginModel.loginId, toLoginModel.loginPw)
            }

            override fun onFailure() {
                myPageView.showInit()
            }
        })
    }

    private fun checkRegister(userId: String, userPass: String, userNickname: String) {
        loginRepository.findUser(
            userId,
            userPass,
            userNickname,
            object : LoginRepositoryCallback.FindUser {
                override fun onSuccess() {
                    changeState(userId, userNickname)
                }

                override fun onFailure() {
                    registerLocal(userId, userPass, userNickname)
                }
            })
    }


    private fun registerLocal(userId: String, userPass: String, userNickname: String) {

        loginRepository.getRegisterData(
            userId,
            userPass,
            userNickname,
            false,
            object : LoginRepositoryCallback.RegisterCallback {
                override fun onSuccess() {
                    changeState(userId, userNickname)
                }

                override fun onFailure() {
                    myPageView.showLoginNo()
                }
            })

    }


    private fun autoLogin(userId: String, userPass: String) {

        userRepository.login(userId, userPass, object : UserRepositoryCallback {
            override fun onSuccess(resultNickname: String) {
                myPageView.showMaintainLogin(userId, resultNickname)
            }

            override fun onFailure(message: String) {
                myPageView.showInit()
            }
        })

    }


    private fun changeState(userId: String, userNickname: String) {
        loginRepository.changeState(userId, true, object : LoginRepositoryCallback.ChangeState {
            override fun onSuccess() {
                myPageView.showLoginOk(userId, userNickname)
            }

            override fun onFailure() {
                myPageView.showLoginNo()
            }
        })
    }

    override fun loginCheck(email: String, pass: String) {
        if (email.isNotEmpty() && pass.isNotEmpty()) {
            if (email.contains(EMPTY_SPACE) || pass.contains(EMPTY_SPACE)) {
                myPageView.showLoginCheck(HAVE_TRIM)
            } else {
                if (RelateLogin.isValidEmail(email)) {
                    myPageView.showLoginCheck(LOGIN_OK)
                } else {
                    myPageView.showLoginCheck(NOT_VALID_EMAIL)
                }
            }
        } else if (email.trim().isNotEmpty() && pass.trim().isEmpty()) {
            myPageView.showLoginCheck(NOT_INPUT_PASS)
        } else if (email.trim().isEmpty() && pass.trim().isNotEmpty()) {
            myPageView.showLoginCheck(NOT_INPUT_EMAIL)
        } else {
            myPageView.showLoginCheck(NOT_INPUT_ALL)
        }

    }

    companion object {

        private const val EMPTY_SPACE = " "

        const val LOGIN_OK = 0
        const val NOT_VALID_EMAIL = 1
        const val HAVE_TRIM = 2
        const val NOT_INPUT_PASS = 3
        const val NOT_INPUT_EMAIL = 4
        const val NOT_INPUT_ALL = 5

    }
}