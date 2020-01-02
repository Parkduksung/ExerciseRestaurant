package com.work.restaurant.data.repository.user

interface UserRepository {
    fun login(email: String, pass: String, callback: UserRepositoryCallback)
    fun register(nickName: String, email: String, pass: String, callback: UserRepositoryCallback)
    fun delete(userNickname: String, userEmail: String, callback: UserRepositoryCallback)
    fun resetPass(email: String,callback: UserRepositoryCallback)
}