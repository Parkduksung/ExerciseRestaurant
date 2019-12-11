package com.work.restaurant.data.source.remote

interface UserDataCallback {
    fun onSuccess(resultNickname: String)
    fun onFailure(message: String)
}