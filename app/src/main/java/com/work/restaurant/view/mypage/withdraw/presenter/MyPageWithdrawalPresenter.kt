package com.work.restaurant.view.mypage.withdraw.presenter

import com.work.restaurant.data.repository.login.LoginRepository
import com.work.restaurant.data.repository.user.UserRepository

class MyPageWithdrawalPresenter(
    private val myPageWithdrawalView: MyPageWithdrawalContract.View,
    private val userRepository: UserRepository,
    private val loginRepository: LoginRepository
) :
    MyPageWithdrawalContract.Presenter {
    override fun withdrawLogin(userNickname: String, userEmail: String) {

        loginRepository.deleteLogin(
            userEmail,
            userNickname,
            callback = { isSuccess ->
                if (isSuccess) {
                    myPageWithdrawalView.showWithdrawLoginOk(userNickname)
                } else {
                    myPageWithdrawalView.showWithdrawNo()
                }
            })
    }

    override fun withdraw(userNickname: String, userEmail: String) {

        userRepository.delete(
            userNickname,
            userEmail,
            callback = { resultNickname ->
                if (resultNickname != null) {
                    myPageWithdrawalView.showWithdrawOk(resultNickname)
                } else {
                    myPageWithdrawalView.showWithdrawNo()
                }
            })
    }

}