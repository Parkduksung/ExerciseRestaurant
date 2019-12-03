package com.work.restaurant.data.repository.mypage

interface MyPageWithdrawRepositoryCallback {

    fun onSuccess()
    fun onFailure(message: String)

}