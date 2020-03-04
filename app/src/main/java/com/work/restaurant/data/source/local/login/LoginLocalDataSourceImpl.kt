package com.work.restaurant.data.source.local.login

import android.util.Log
import com.work.restaurant.network.room.database.LoginDatabase
import com.work.restaurant.network.room.entity.LoginEntity
import com.work.restaurant.util.AppExecutors

class LoginLocalDataSourceImpl(
    private val appExecutors: AppExecutors,
    private val loginDatabase: LoginDatabase
) : LoginLocalDataSource {

    override fun deleteLogin(
        id: String,
        nickname: String,
        callback: LoginLocalDataSourceCallback.DeleteCallback
    ) {
        appExecutors.diskIO.execute {

            val deleteLogin = loginDatabase.loginDao().deleteLogin(id, nickname)

            appExecutors.mainThread.execute {
                if (deleteLogin >= 1) {
                    callback.onSuccess()
                } else {
                    callback.onFailure()
                }
            }

        }
    }


    override fun changeState(
        id: String,
        state: Boolean,
        callback: LoginLocalDataSourceCallback.ChangeState
    ) {
        appExecutors.diskIO.execute {

            val changeState = loginDatabase.loginDao().stateChange(id = id, state = state)

            appExecutors.mainThread.execute {

                if (changeState == 1) {
                    callback.onSuccess()
                } else {
                    callback.onFailure()
                }

            }

        }

    }

    override fun getRegisterData(
        id: String,
        pw: String,
        nickname: String,
        state: Boolean,
        callback: LoginLocalDataSourceCallback.RegisterCallback
    ) {

        appExecutors.diskIO.execute {

            val loginEntity = LoginEntity(
                loginId = id,
                loginPw = pw,
                loginNickname = nickname,
                loginState = state
            )

            val registerLogin = loginDatabase.loginDao().registerLogin(loginEntity)

            appExecutors.mainThread.execute {
                if (registerLogin >= 1) {
                    callback.onSuccess()
                } else {
                    callback.onFailure()
                }
            }

        }


    }


    override fun getLoginState(callback: LoginLocalDataSourceCallback.LoginStateCallback) {


        appExecutors.diskIO.execute {


            val loginListOfTrue = loginDatabase.loginDao().getLoginState(true)

            val loginListOfTrue1 = loginDatabase.loginDao().getAllList()

            loginListOfTrue1.forEach {
                Log.d("결과가머더냐아이디들?", it.loginId)
            }
//
////
//            loginDatabase.loginDao().delete(loginListOfTrue1)
//
//            if (loginDatabase.loginDao().delete(loginListOfTrue) == 1) {
//                Log.d("결과가머더냐?", loginListOfTrue.size.toString())
//            }

            Log.d("결과가머더냐?", loginListOfTrue.size.toString())
////
            Log.d("결과가머더냐총갯수?", loginListOfTrue1.size.toString())

            appExecutors.mainThread.execute {

                if (loginListOfTrue.isNotEmpty() && loginListOfTrue.size == 1) {
                    callback.onSuccess(loginListOfTrue[0])
                } else {
                    callback.onFailure()
                }
            }

        }


    }


    companion object {

        private var instance: LoginLocalDataSourceImpl? = null

        fun getInstance(
            appExecutors: AppExecutors,
            loginDatabase: LoginDatabase
        ): LoginLocalDataSourceImpl =
            instance ?: LoginLocalDataSourceImpl(
                appExecutors,
                loginDatabase
            ).also {
                instance = it
            }
    }

}