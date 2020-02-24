package com.work.restaurant.view.mypage.find.presenter

import com.work.restaurant.data.repository.user.UserRepository
import com.work.restaurant.data.repository.user.UserRepositoryCallback

class MyPageFindPassPresenter(
    private val myPageFindPassView: MyPageFindPassContract.View,
    private val userRepository: UserRepository
) :
    MyPageFindPassContract.Presenter {
    override fun resetPass(email: String) {

        userRepository.resetPass(email, object : UserRepositoryCallback {
            override fun onSuccess(resultNickname: String) {

                if (resultNickname.isNotEmpty()) {
                    myPageFindPassView.showResetOk()
                }
            }

            override fun onFailure(message: String) {
                myPageFindPassView.showResetNo(message)
            }
        })
    }


}