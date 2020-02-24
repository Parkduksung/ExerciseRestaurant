package com.work.restaurant.data.repository.login

interface LoginRepository {

    fun getLoginState(
        callback: LoginRepositoryCallback.LoginStateCallback
    )

    fun getRegisterData(
        id: String,
        pw: String,
        nickname: String,
        state: Boolean,
        callback: LoginRepositoryCallback.RegisterCallback
    )

    fun changeState(
        id: String,
        state: Boolean,
        callback: LoginRepositoryCallback.ChangeState
    )

    fun deleteLogin(
        id:String,
        nickname: String,
        callback: LoginRepositoryCallback.DeleteCallback
    )

}