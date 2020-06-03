package com.work.restaurant.data.repository.login

import com.work.restaurant.network.room.entity.LoginEntity

interface LoginRepository {

    fun getLoginState(
        state: Boolean,
        callback: (list: LoginEntity?) -> Unit
    )

    fun getRegisterData(
        id: String,
        pw: String,
        nickname: String,
        state: Boolean,
        callback: (isSuccess: Boolean) -> Unit
    )

    fun changeState(
        id: String,
        state: Boolean,
        callback: (isSuccess: Boolean) -> Unit
    )

    fun deleteLogin(
        id: String,
        nickname: String,
        callback: (isSuccess: Boolean) -> Unit
    )

    fun findUser(
        id: String,
        pw: String,
        nickname: String,
        callback: (isSuccess: Boolean) -> Unit
    )

}