package com.work.restaurant.data.repository.mypage

interface UserRepository {
    fun login(email: String, pass: String, callback: UserRepositoryCallback)
    fun register(nickName: String, email: String, pass: String, callback: UserRepositoryCallback)
    fun delete(userNickname: String, callback: UserRepositoryCallback)
    fun modify()
}