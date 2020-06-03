package com.work.restaurant.data.source.local.login

import com.work.restaurant.network.room.database.LoginDatabase
import com.work.restaurant.network.room.entity.LoginEntity
import com.work.restaurant.util.AppExecutors

class LoginLocalDataSourceImpl(
    private val appExecutors: AppExecutors,
    private val loginDatabase: LoginDatabase
) : LoginLocalDataSource {
    override fun getLoginState(
        state: Boolean,
        callback: (list: LoginEntity?) -> Unit
    ) {
        appExecutors.diskIO.execute {
            val loginListOfTrue =
                loginDatabase.loginDao().getLoginState(state)

            appExecutors.mainThread.execute {

                if (loginListOfTrue.isNotEmpty() && loginListOfTrue.size == 1) {
                    callback(loginListOfTrue[0])
                } else {
                    callback(null)
                }
            }

        }
    }

    override fun getRegisterData(
        id: String,
        pw: String,
        nickname: String,
        state: Boolean,
        callback: (isSuccess: Boolean) -> Unit
    ) {

        appExecutors.diskIO.execute {

            val loginEntity =
                LoginEntity(
                    loginId = id,
                    loginPw = pw,
                    loginNickname = nickname,
                    loginState = state
                )

            val registerLogin =
                loginDatabase.loginDao().registerLogin(loginEntity)

            appExecutors.mainThread.execute {
                if (registerLogin >= 1) {
                    callback(true)
                } else {
                    callback(false)
                }
            }

        }
    }

    override fun changeState(
        id: String,
        state: Boolean,
        callback: (isSuccess: Boolean) -> Unit
    ) {
        appExecutors.diskIO.execute {

            val changeState =
                loginDatabase.loginDao().stateChange(id = id, state = state)

            appExecutors.mainThread.execute {

                if (changeState >= 1) {
                    callback(true)
                } else {
                    callback(false)
                }
            }
        }
    }

    override fun deleteLogin(
        id: String,
        nickname: String,
        callback: (isSuccess: Boolean) -> Unit
    ) {
        appExecutors.diskIO.execute {

            val deleteLogin =
                loginDatabase.loginDao().deleteLogin(id, nickname)

            appExecutors.mainThread.execute {
                if (deleteLogin >= 1) {
                    callback(true)
                } else {
                    callback(false)
                }
            }

        }
    }

    override fun findUser(
        id: String,
        pw: String,
        nickname: String,
        callback: (isSuccess: Boolean) -> Unit
    ) {
        appExecutors.diskIO.execute {

            val findUser =
                loginDatabase.loginDao().findUser(id, pw, nickname)

            appExecutors.mainThread.execute {

                if (findUser > 1) {
                    callback(true)
                } else {
                    callback(false)
                }
            }
        }
    }
}