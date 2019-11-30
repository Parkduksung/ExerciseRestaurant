package com.work.restaurant.data.repository.mypage

interface MyPageLoginRepository {
    fun loginResult(email: String, pass: String, callback: MyPageLoginRepositoryCallback)
}