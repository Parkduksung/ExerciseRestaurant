package com.work.restaurant.data.repository.mypage

import com.work.restaurant.data.source.remote.MyPageRegisterData
import com.work.restaurant.data.source.remote.MyPageRegisterDataCallback

class MyPageRegisterRepositoryImpl private constructor(private val myPageRegisterData: MyPageRegisterData) :
    MyPageRegisterRepository {
    override fun getRegisterData(
        nickName: String,
        email: String,
        pass: String,
        callback: MyPageRegisterRepositoryCallback
    ) {
        myPageRegisterData.getRegisterData(nickName, email, pass, object : MyPageRegisterDataCallback {
            override fun onSuccess() {
                callback.onSuccess()
            }

            override fun onFailure(message: String) {
                callback.onFailure(message)
            }
        })
    }

    companion object {

        private var instance: MyPageRegisterRepositoryImpl? = null
        fun getInstance(
            myPageRegisterData: MyPageRegisterData
        ): MyPageRegisterRepositoryImpl =
            instance ?: MyPageRegisterRepositoryImpl(myPageRegisterData).also {
                instance = it
            }


    }

}