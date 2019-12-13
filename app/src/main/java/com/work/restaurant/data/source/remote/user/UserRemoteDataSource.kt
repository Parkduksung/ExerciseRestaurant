package com.work.restaurant.data.source.remote.user

interface UserRemoteDataSource {

    fun login(email: String, pass: String, callbackRemoteSource: UserRemoteDataSourceCallback)
    fun register(nickName: String, email: String, pass: String, callbackRemoteSource: UserRemoteDataSourceCallback)
    fun delete(userNickname: String, callbackRemoteSource: UserRemoteDataSourceCallback)
    fun modify()

}