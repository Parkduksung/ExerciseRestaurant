package com.work.restaurant.data.source.remote

interface MyPageWithdrawData {
    fun getWithdrawData(userNickname : String , callback : MyPageWithdrawDataCallback)
}