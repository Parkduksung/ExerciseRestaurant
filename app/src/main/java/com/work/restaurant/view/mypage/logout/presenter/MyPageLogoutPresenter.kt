package com.work.restaurant.view.mypage.logout.presenter

import com.work.restaurant.data.repository.login.LoginRepository

class MyPageLogoutPresenter(
    private val myPageLogoutView: MyPageLogoutContract.View,
    private val loginRepository: LoginRepository
) :
    MyPageLogoutContract.Presenter {

    override fun logoutOk(userId: String) {

        loginRepository.changeState(
            userId,
            LOGOUT_STATE,
            callback = { isSuccess ->
                if (isSuccess) {
                    myPageLogoutView.showLogoutOk()
                } else {
                    myPageLogoutView.showLogoutNo()
                }
            })
    }

    companion object {

        private const val LOGOUT_STATE = false
    }

}