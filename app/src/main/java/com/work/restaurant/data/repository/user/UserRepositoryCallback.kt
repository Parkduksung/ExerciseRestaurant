package com.work.restaurant.data.repository.user

interface UserRepositoryCallback {

    fun onSuccess(resultNickname: String)
    fun onFailure(message: String)
}