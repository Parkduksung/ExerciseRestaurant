package com.work.restaurant.data.repository.mypage

interface MyPageWithdrawRepository {

    fun withdrawResult(userNickname: String, callback: MyPageWithdrawRepositoryCallback)
}