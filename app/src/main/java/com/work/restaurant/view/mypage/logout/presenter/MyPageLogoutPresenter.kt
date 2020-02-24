package com.work.restaurant.view.mypage.logout.presenter

import com.work.restaurant.data.repository.login.LoginRepository
import com.work.restaurant.data.repository.login.LoginRepositoryCallback

class MyPageLogoutPresenter(
    private val myPageLogoutView: MyPageLogoutContract.View,
    private val loginRepository: LoginRepository
) :
    MyPageLogoutContract.Presenter {

    override fun logoutOk(userId: String) {

        loginRepository.changeState(userId, false, object : LoginRepositoryCallback.ChangeState {
            override fun onSuccess() {
                myPageLogoutView.showLogoutOk()
            }

            override fun onFailure() {
                myPageLogoutView.showLogoutNo()
            }
        })
    }


}