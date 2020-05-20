package com.work.restaurant.view.mypage.find.presenter

import com.work.restaurant.data.repository.user.UserRepository

class MyPageFindPassPresenter(
    private val myPageFindPassView: MyPageFindPassContract.View,
    private val userRepository: UserRepository
) :
    MyPageFindPassContract.Presenter {
    override fun resetPass(email: String) {

        userRepository.resetPass(
            email,
            callback = { resultNickname ->
                if (resultNickname != null) {
                    if (resultNickname.isNotEmpty()) {
                        myPageFindPassView.showResetOk()
                    } else {
                        myPageFindPassView.showResetNo()
                    }

                } else {
                    myPageFindPassView.showResetNo()
                }
            })
    }


}