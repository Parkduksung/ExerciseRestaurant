package com.work.restaurant.data.repository.login

import com.work.restaurant.data.source.local.login.LoginLocalDataSourceCallback
import com.work.restaurant.data.source.local.login.LoginLocalDataSourceImpl
import com.work.restaurant.network.room.entity.LoginEntity

class LoginRepositoryImpl(
    private val loginLocalDataSourceImpl: LoginLocalDataSourceImpl
) : LoginRepository {

    override fun findUser(
        id: String,
        pw: String,
        nickname: String,
        callback: LoginRepositoryCallback.FindUser
    ) {

        loginLocalDataSourceImpl.findUser(
            id,
            pw,
            nickname,
            object : LoginLocalDataSourceCallback.FindUser {
                override fun onSuccess() {
                    callback.onSuccess()
                }

                override fun onFailure() {
                    callback.onFailure()
                }
            })

    }

    override fun changeState(
        id: String,
        state: Boolean,
        callback: LoginRepositoryCallback.ChangeState
    ) {
        loginLocalDataSourceImpl.changeState(
            id,
            state,
            object : LoginLocalDataSourceCallback.ChangeState {
                override fun onSuccess() {
                    callback.onSuccess()
                }

                override fun onFailure() {
                    callback.onFailure()
                }
            })
    }

    override fun deleteLogin(
        id: String,
        nickname: String,
        callback: LoginRepositoryCallback.DeleteCallback
    ) {
        loginLocalDataSourceImpl.deleteLogin(
            id,
            nickname,
            object : LoginLocalDataSourceCallback.DeleteCallback {
                override fun onSuccess() {
                    callback.onSuccess()
                }

                override fun onFailure() {
                    callback.onFailure()
                }
            })


    }

    override fun getLoginState(callback: LoginRepositoryCallback.LoginStateCallback) {


        loginLocalDataSourceImpl.getLoginState(object :
            LoginLocalDataSourceCallback.LoginStateCallback {
            override fun onSuccess(list: LoginEntity) {
                callback.onSuccess(list)
            }

            override fun onFailure() {
                callback.onFailure()
            }
        })


    }

    override fun getRegisterData(
        id: String,
        pw: String,
        nickname: String,
        state: Boolean,
        callback: LoginRepositoryCallback.RegisterCallback
    ) {
        loginLocalDataSourceImpl.getRegisterData(
            id,
            pw,
            nickname,
            true,
            object : LoginLocalDataSourceCallback.RegisterCallback {
                override fun onSuccess() {
                    callback.onSuccess()
                }

                override fun onFailure() {
                    callback.onFailure()
                }
            }

        )


    }


    companion object {

        private var instance: LoginRepositoryImpl? = null

        fun getInstance(
            loginLocalDataSourceImpl: LoginLocalDataSourceImpl
        ): LoginRepositoryImpl =
            instance ?: LoginRepositoryImpl(loginLocalDataSourceImpl)
                .also {
                    instance = it
                }

    }


}