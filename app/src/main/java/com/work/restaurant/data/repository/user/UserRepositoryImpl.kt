package com.work.restaurant.data.repository.user

import com.work.restaurant.data.source.remote.user.UserRemoteDataSource
import com.work.restaurant.ext.isConnectedToNetwork
import com.work.restaurant.util.App

class UserRepositoryImpl(private val userRemoteDataSource: UserRemoteDataSource) :
    UserRepository {

    override fun login(email: String, pass: String, callback: (resultNickname: String?) -> Unit) {
        if (App.instance.context().isConnectedToNetwork()) {
            userRemoteDataSource.login(email, pass, callback)
        }
    }

    override fun register(
        nickName: String,
        email: String,
        pass: String,
        callback: (resultNickname: String?) -> Unit
    ) {
        if (App.instance.context().isConnectedToNetwork()) {
            userRemoteDataSource.register(nickName, email, pass, callback)
        }
    }

    override fun delete(
        userNickname: String,
        userEmail: String,
        callback: (resultNickname: String?) -> Unit
    ) {
        if (App.instance.context().isConnectedToNetwork()) {
            userRemoteDataSource.delete(userNickname, userEmail, callback)
        }
    }

    override fun resetPass(email: String, callback: (resultNickname: String?) -> Unit) {
        if (App.instance.context().isConnectedToNetwork()) {
            userRemoteDataSource.resetPass(email, callback)
        }
    }

    override fun emailDuplicationCheck(email: String, callback: (isSuccess: Boolean) -> Unit) {
        if (App.instance.context().isConnectedToNetwork()) {
            userRemoteDataSource.emailDuplicationCheck(email, callback)
        }
    }
}