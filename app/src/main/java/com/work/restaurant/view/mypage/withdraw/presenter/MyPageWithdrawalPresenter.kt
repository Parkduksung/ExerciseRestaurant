package com.work.restaurant.view.mypage.withdraw.presenter

import com.work.restaurant.data.repository.user.UserRepositoryCallback
import com.work.restaurant.data.repository.user.UserRepositoryImpl
import com.work.restaurant.data.source.remote.user.UserRemoteRemoteDataSourceSourceImpl
import com.work.restaurant.network.RetrofitInstance
import com.work.restaurant.view.mypage.main.MyPageFragment.Companion.URL

class MyPageWithdrawalPresenter(private val myPageWithdrawalView: MyPageWithdrawalContract.View) :
    MyPageWithdrawalContract.Presenter {

    override fun withdrawCancel() {
        myPageWithdrawalView.showWithdrawCancel()
    }

    override fun withdraw(userNickname: String, userEmail: String) {

        UserRepositoryImpl.getInstance(
            UserRemoteRemoteDataSourceSourceImpl.getInstance(
                RetrofitInstance.getInstance(URL)
            )
        )
            .delete(userNickname, userEmail, object : UserRepositoryCallback {
                override fun onSuccess(resultNickname: String) {
                    myPageWithdrawalView.showWithdrawOk(resultNickname)
                }

                override fun onFailure(message: String) {
                    myPageWithdrawalView.showWithdrawNo()
                }
            })

    }

}