package com.work.restaurant.data.source.local.login

interface LoginLocalDataSource {

    fun getLoginState(
        callback: LoginLocalDataSourceCallback.LoginStateCallback
    )

    fun getRegisterData(
        id: String,
        pw: String,
        nickname: String,
        state: Boolean,
        callback: LoginLocalDataSourceCallback.RegisterCallback
    )

    fun changeState(
        id: String,
        state: Boolean,
        callback: LoginLocalDataSourceCallback.ChangeState
    )

    fun deleteLogin(
        id:String,
        nickname: String,
        callback: LoginLocalDataSourceCallback.DeleteCallback
    )

}