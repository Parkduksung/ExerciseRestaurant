package com.work.restaurant.data.source.local.login

import com.work.restaurant.db.room.entity.LoginEntity

interface LoginLocalDataSource {

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