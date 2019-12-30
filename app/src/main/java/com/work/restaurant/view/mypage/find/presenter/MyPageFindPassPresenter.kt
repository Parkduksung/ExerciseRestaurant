package com.work.restaurant.view.mypage.find.presenter

import com.work.restaurant.data.repository.user.UserRepositoryCallback
import com.work.restaurant.data.repository.user.UserRepositoryImpl
import com.work.restaurant.data.source.remote.user.UserRemoteRemoteDataSourceSourceImpl
import com.work.restaurant.network.RetrofitInstance
import com.work.restaurant.view.mypage.main.MyPageFragment.Companion.URL

class MyPageFindPassPresenter(private val myPageFindPassView: MyPageFindPassContract.View) :
    MyPageFindPassContract.Presenter {
    override fun resetPass(email: String) {

        UserRepositoryImpl.getInstance(
            UserRemoteRemoteDataSourceSourceImpl.getInstance(
                RetrofitInstance.getInstance(URL)
            )
        ).resetPass(email, object : UserRepositoryCallback {
            override fun onSuccess(resultNickname: String) {
                myPageFindPassView.showResetOk(resultNickname)
            }

            override fun onFailure(message: String) {
                myPageFindPassView.showResetNo(message)
            }
        })

    }

    override fun backPage() {
        myPageFindPassView.showBackPage()
    }
}