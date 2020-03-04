package com.work.restaurant.view.mypage.main.presenter

import com.work.restaurant.data.repository.login.LoginRepository
import com.work.restaurant.data.repository.login.LoginRepositoryCallback
import com.work.restaurant.data.repository.user.UserRepository
import com.work.restaurant.data.repository.user.UserRepositoryCallback
import com.work.restaurant.network.room.entity.LoginEntity

class MyPagePresenter(
    private val myPageView: MyPageContract.View,
    private val loginRepository: LoginRepository,
    private val userRepository: UserRepository
) : MyPageContract.Presenter {


    override fun login(email: String, pass: String) {
        userRepository.login(email, pass, object : UserRepositoryCallback {
            override fun onSuccess(resultNickname: String) {
                changeState(email, resultNickname)
            }

            override fun onFailure(message: String) {
                myPageView.showLoginNo()
            }
        })
    }

    override fun getLoginState() {
        loginRepository.getLoginState(object : LoginRepositoryCallback.LoginStateCallback {
            override fun onSuccess(list: LoginEntity) {
                val toLoginModel = list.toLoginModel()
                autoLogin(toLoginModel.loginId, toLoginModel.loginPw)
            }

            override fun onFailure() {
                //현재 로그인된 계정이 없는것
                myPageView.showInit()
            }
        })
    }

    private fun autoLogin(userId: String, userPass: String) {

        userRepository.login(userId, userPass, object : UserRepositoryCallback {
            override fun onSuccess(resultNickname: String) {
                myPageView.showMaintainLogin(userId, resultNickname)
            }

            override fun onFailure(message: String) {
                //자동로그인이 안되는것.
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


}