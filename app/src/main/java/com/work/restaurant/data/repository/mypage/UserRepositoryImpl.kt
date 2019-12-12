package com.work.restaurant.data.repository.mypage

import com.work.restaurant.data.source.remote.UserDataCallback
import com.work.restaurant.data.source.remote.UserRemoteDataSourceImpl
import com.work.restaurant.ext.isConnectedToNetwork
import com.work.restaurant.util.App

class UserRepositoryImpl private constructor(private val userRemoteDataSourceImpl: UserRemoteDataSourceImpl) :
    UserRepository {
    override fun login(email: String, pass: String, callback: UserRepositoryCallback) {

        if (App.instance.context().isConnectedToNetwork()) {
            userRemoteDataSourceImpl.login(email, pass, object : UserDataCallback {
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
            userRemoteDataSourceImpl.register(nickName, email, pass, object : UserDataCallback {
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
            userRemoteDataSourceImpl.delete(userNickname, object : UserDataCallback {
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
            userRemoteDataSourceImpl: UserRemoteDataSourceImpl
        ): UserRepositoryImpl =
            instance ?: UserRepositoryImpl(userRemoteDataSourceImpl).also {
                instance = it
            }


    }


}