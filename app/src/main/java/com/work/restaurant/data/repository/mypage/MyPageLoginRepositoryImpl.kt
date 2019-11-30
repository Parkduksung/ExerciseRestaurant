package com.work.restaurant.data.repository.mypage

import com.work.restaurant.data.source.remote.MyPageLoginData
import com.work.restaurant.data.source.remote.MyPageLoginDataCallback

class MyPageLoginRepositoryImpl private constructor(
    private val myPageLoginData: MyPageLoginData
) : MyPageLoginRepository {

    override fun loginResult(email: String, pass: String, callback: MyPageLoginRepositoryCallback) {


        myPageLoginData.getLoginData(email, pass, object : MyPageLoginDataCallback {
            override fun onSuccess(resultNickname: String) {
                callback.onSuccess(resultNickname)
            }

            override fun onFailure(message: String) {
                callback.onFailure(message)
            }
        })


    }

    companion object {

        private var instance: MyPageLoginRepositoryImpl? = null
        fun getInstance(
            myPageLoginData: MyPageLoginData
        ): MyPageLoginRepositoryImpl =
            instance ?: MyPageLoginRepositoryImpl(myPageLoginData).also {
                instance = it
            }


    }


}