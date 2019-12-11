package com.work.restaurant.data.source.remote

interface UserData {

    fun login(email: String, pass: String, callback: UserDataCallback)
    fun register(nickName: String, email: String, pass: String, callback: UserDataCallback)
    fun delete(userNickname: String, callback: UserDataCallback)
    fun modify()

}