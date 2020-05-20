package com.work.restaurant.data.repository.user

interface UserRepository {
    fun login(
        email: String,
        pass: String,
        callback: (resultNickname: String?) -> Unit
    )

    fun register(
        nickName: String,
        email: String,
        pass: String,
        callback: (resultNickname: String?) -> Unit
    )

    fun delete(
        userNickname: String,
        userEmail: String,
        callback: (resultNickname: String?) -> Unit
    )

    fun resetPass(
        email: String,
        callback: (resultNickname: String?) -> Unit
    )

    fun emailDuplicationCheck(
        email: String,
        callback: (isSuccess: Boolean) -> Unit
    )
}