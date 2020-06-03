package com.work.restaurant.data.repository.login

import com.work.restaurant.data.source.local.login.LoginLocalDataSource
import com.work.restaurant.network.room.entity.LoginEntity

class LoginRepositoryImpl(
    private val loginLocalDataSource: LoginLocalDataSource
) : LoginRepository {
    override fun getLoginState(
        state: Boolean,
        callback: (list: LoginEntity?) -> Unit
    ) {
        loginLocalDataSource.getLoginState(state, callback)
    }

    override fun getRegisterData(
        id: String,
        pw: String,
        nickname: String,
        state: Boolean,
        callback: (isSuccess: Boolean) -> Unit
    ) {
        loginLocalDataSource.getRegisterData(id, pw, nickname, state, callback)
    }

    override fun changeState(id: String, state: Boolean, callback: (isSuccess: Boolean) -> Unit) {
        loginLocalDataSource.changeState(id, state, callback)
    }

    override fun deleteLogin(id: String, nickname: String, callback: (isSuccess: Boolean) -> Unit) {
        loginLocalDataSource.deleteLogin(id, nickname, callback)
    }

    override fun findUser(
        id: String,
        pw: String,
        nickname: String,
        callback: (isSuccess: Boolean) -> Unit
    ) {
        loginLocalDataSource.findUser(id, pw, nickname, callback)
    }
}