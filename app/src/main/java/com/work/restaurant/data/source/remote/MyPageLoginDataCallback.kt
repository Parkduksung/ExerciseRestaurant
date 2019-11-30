package com.work.restaurant.data.source.remote

interface MyPageLoginDataCallback {

    fun onSuccess(resultNickname: String)

    fun onFailure(message: String)
}