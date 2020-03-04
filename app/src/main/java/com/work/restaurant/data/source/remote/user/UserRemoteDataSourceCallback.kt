package com.work.restaurant.data.source.remote.user

interface UserRemoteDataSourceCallback {
    fun onSuccess(resultNickname: String)
    fun onFailure(message: String)


    interface EmailDuplicationCheck {
        fun onSuccess()
        fun onFailure()
    }

}