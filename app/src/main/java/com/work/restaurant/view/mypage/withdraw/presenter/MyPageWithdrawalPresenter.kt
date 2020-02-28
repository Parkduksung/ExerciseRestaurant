package com.work.restaurant.view.mypage.withdraw.presenter

import com.work.restaurant.data.repository.login.LoginRepository
import com.work.restaurant.data.repository.login.LoginRepositoryCallback
import com.work.restaurant.data.repository.user.UserRepository
import com.work.restaurant.data.repository.user.UserRepositoryCallback

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
            object : LoginRepositoryCallback.DeleteCallback {
                override fun onSuccess() {
                    myPageWithdrawalView.showWithdrawLoginOk(userNickname)
                }

                override fun onFailure() {
                    myPageWithdrawalView.showWithdrawNo(1)
                }
            })

    }

    override fun withdrawCancel() {
        myPageWithdrawalView.showWithdrawCancel()
    }

    override fun withdraw(userNickname: String, userEmail: String) {

        userRepository.delete(userNickname, userEmail, object : UserRepositoryCallback {
            override fun onSuccess(resultNickname: String) {
                myPageWithdrawalView.showWithdrawOk(resultNickname)
            }

            override fun onFailure(message: String) {
                myPageWithdrawalView.showWithdrawNo(0)
            }
        })


    }

}