package com.work.restaurant.data.repository.mypage

import com.work.restaurant.data.source.remote.MyPageWithdrawData
import com.work.restaurant.data.source.remote.MyPageWithdrawDataCallback

class MyPageWithdrawRepositoryImpl private constructor(
    private val myPageWithdrawData: MyPageWithdrawData
) : MyPageWithdrawRepository {

    override fun withdrawResult(userNickname: String, callback: MyPageWithdrawRepositoryCallback) {

        myPageWithdrawData.getWithdrawData(userNickname, object : MyPageWithdrawDataCallback {
            override fun onSuccess() {
                callback.onSuccess()
            }
            override fun onFailure(message: String) {
                callback.onFailure(message)
            }
        })

    }

    companion object {

        private var instance: MyPageWithdrawRepositoryImpl? = null
        fun getInstance(
            myPageWithdrawData: MyPageWithdrawData
        ): MyPageWithdrawRepositoryImpl =
            instance ?: MyPageWithdrawRepositoryImpl(myPageWithdrawData).also {
                instance = it
            }


    }

}