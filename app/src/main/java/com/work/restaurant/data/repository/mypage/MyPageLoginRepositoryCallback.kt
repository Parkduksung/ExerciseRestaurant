package com.work.restaurant.data.repository.mypage

interface MyPageLoginRepositoryCallback {

    fun onSuccess(resultNickname: String)
    fun onFailure(message: String)
}