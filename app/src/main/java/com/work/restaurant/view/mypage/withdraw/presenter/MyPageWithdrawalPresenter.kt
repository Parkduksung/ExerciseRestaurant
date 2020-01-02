package com.work.restaurant.view.mypage.withdraw.presenter

import com.work.restaurant.data.repository.user.UserRepository
import com.work.restaurant.data.repository.user.UserRepositoryCallback

class MyPageWithdrawalPresenter(
    private val myPageWithdrawalView: MyPageWithdrawalContract.View,
    private val userRepository: UserRepository
) :
    MyPageWithdrawalContract.Presenter {

    override fun withdrawCancel() {
        myPageWithdrawalView.showWithdrawCancel()
    }

    override fun withdraw(userNickname: String, userEmail: String) {

        userRepository.delete(userNickname, userEmail, object : UserRepositoryCallback {
            override fun onSuccess(resultNickname: String) {
                myPageWithdrawalView.showWithdrawOk(resultNickname)
            }

            override fun onFailure(message: String) {
                myPageWithdrawalView.showWithdrawNo()
            }
        })


    }

}