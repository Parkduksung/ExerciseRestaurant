package com.work.restaurant.data.repository.mypage

import com.work.restaurant.data.source.remote.UserDataCallback
import com.work.restaurant.data.source.remote.UserDataImpl
import com.work.restaurant.ext.isConnectedToNetwork
import com.work.restaurant.util.App

class UserRepositoryImpl private constructor(private val userDataImpl: UserDataImpl) :
    UserRepository {
    override fun login(email: String, pass: String, callback: UserRepositoryCallback) {

        if (App.instance.context().isConnectedToNetwork()) {
            userDataImpl.login(email, pass, object : UserDataCallback {
                override fun onSuccess(resultNickname: String) {
                    callback.onSuccess(resultNickname)
                }

                override fun onFailure(message: String) {
                    callback.onFailure(message)
                }

            })
        }

    }

    override fun register(
        nickName: String,
        email: String,
        pass: String,
        callback: UserRepositoryCallback
    ) {

        if (App.instance.context().isConnectedToNetwork()) {
            userDataImpl.register(nickName, email, pass, object : UserDataCallback {
                override fun onSuccess(resultNickname: String) {
                    callback.onSuccess(resultNickname)
                }

                override fun onFailure(message: String) {
                    callback.onFailure(message)
                }
            })
        }

    }

    override fun delete(userNickname: String, callback: UserRepositoryCallback) {

        if (App.instance.context().isConnectedToNetwork()) {
            userDataImpl.delete(userNickname, object : UserDataCallback {
                override fun onSuccess(resultNickname: String) {
                    callback.onSuccess(resultNickname)
                }

                override fun onFailure(message: String) {
                    callback.onFailure(message)
                }
            })
        }
    }

    override fun modify() {

        if (App.instance.context().isConnectedToNetwork()) {

        }


    }


    companion object {

        private var instance: UserRepositoryImpl? = null
        fun getInstance(
            userDataImpl: UserDataImpl
        ): UserRepositoryImpl =
            instance ?: UserRepositoryImpl(userDataImpl).also {
                instance = it
            }


    }


}