package com.work.restaurant.data.repository.mypage

interface UserRepositoryCallback {

    fun onSuccess(resultNickname: String)
    fun onFailure(message: String)
}