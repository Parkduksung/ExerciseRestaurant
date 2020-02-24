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
    override fun loginFirebase(userId: String, userPass: String, userNickname: String) {
        userRepository.login(userId, userPass, object : UserRepositoryCallback {
            override fun onSuccess(resultNickname: String) {

                myPageView.showFirebaseLogin(userId, resultNickname)

            }

            override fun onFailure(message: String) {
                myPageView.showInit()
            }
        })


    }

    override fun getLoginState() {
        loginRepository.getLoginState(object : LoginRepositoryCallback.LoginStateCallback {
            override fun onSuccess(list: LoginEntity) {

                val toLoginModel = list.toLoginModel()
                myPageView.showLoginState(toLoginModel)
            }

            override fun onFailure() {
                myPageView.showInit()
            }
        })
    }


}