package com.work.restaurant.view.mypage.presenter

import com.work.restaurant.data.repository.mypage.UserRepositoryCallback
import com.work.restaurant.data.repository.mypage.UserRepositoryImpl
import com.work.restaurant.data.source.remote.UserRemoteDataSourceImpl
import com.work.restaurant.network.RetrofitInstance
import com.work.restaurant.view.mypage.contract.MyPageWithdrawalContract
import com.work.restaurant.view.mypage.fragment.MyPageLoginFragment.Companion.URL

class MyPageWithdrawalPresenter(private val myPageWithdrawalView: MyPageWithdrawalContract.View) :
    MyPageWithdrawalContract.Presenter {

    override fun withdrawCancel() {
        myPageWithdrawalView.showWithdrawCancel()
    }

    override fun withdraw(userNickname: String) {

        UserRepositoryImpl.getInstance(UserRemoteDataSourceImpl.getInstance(RetrofitInstance.getInstance(URL)))
            .delete(userNickname, object : UserRepositoryCallback {
                override fun onSuccess(resultNickname: String) {
                    myPageWithdrawalView.showWithdrawOk(resultNickname)
                }

                override fun onFailure(message: String) {
                    myPageWithdrawalView.showWithdrawNo()
                }
            })

    }

}