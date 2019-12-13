package com.work.restaurant.data.source.remote.user

import android.util.Log
import com.work.restaurant.network.api.UserApi
import com.work.restaurant.network.model.ResultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRemoteRemoteDataSourceSourceImpl private constructor(private val userApi: UserApi) :
    UserRemoteDataSource {

    override fun login(email: String, pass: String, callbackRemoteSource: UserRemoteDataSourceCallback) {

        userApi.login(email, pass).enqueue(object :
            Callback<ResultResponse> {
            override fun onFailure(call: Call<ResultResponse>?, t: Throwable?) {
                callbackRemoteSource.onFailure("${t?.message}")
            }

            override fun onResponse(
                call: Call<ResultResponse>?,
                response: Response<ResultResponse>?
            ) {

                val result = response?.body()?.result

                val resultNickname = response?.body()?.resultNickname

                if (result != null && resultNickname != null) {
                    if (result) {
                        callbackRemoteSource.onSuccess(resultNickname)
                    } else {
                        callbackRemoteSource.onFailure("등록된 아이디가 없습니다.")
                    }
                } else {
                    callbackRemoteSource.onFailure("로그인 실패")
                }

            }
        })

    }

    override fun register(
        nickName: String,
        email: String,
        pass: String,
        callbackRemoteSource: UserRemoteDataSourceCallback
    ) {

        userApi.register(
            nickName,
            email,
            pass
        ).enqueue(object : Callback<ResultResponse> {
            override fun onFailure(call: Call<ResultResponse>?, t: Throwable?) {
                callbackRemoteSource.onFailure("${t?.message}")
            }

            override fun onResponse(
                call: Call<ResultResponse>?,
                response: Response<ResultResponse>?
            ) {
                val result = response?.body()?.result


                result?.let {
                    if (it) {
                        Log.d("1111111", "성공")
                        callbackRemoteSource.onSuccess(nickName)
                    }

                }

//                if (result == true) {
//                    Log.d("1111111", "성공")
//                    callback.onSuccess(nickName)
//                }
            }
        })


    }

    override fun delete(userNickname: String, callbackRemoteSource: UserRemoteDataSourceCallback) {

        userApi.delete(userNickname).enqueue(object :
            Callback<ResultResponse> {
            override fun onFailure(call: Call<ResultResponse>?, t: Throwable?) {
                callbackRemoteSource.onFailure("${t?.message}")
            }

            override fun onResponse(
                call: Call<ResultResponse>?,
                response: Response<ResultResponse>?
            ) {

                val result = response?.body()?.result

                if (result != null) {
                    if (result) {
                        callbackRemoteSource.onSuccess(userNickname)
                    } else {
                        callbackRemoteSource.onFailure("삭제 실패")
                    }
                } else {
                    callbackRemoteSource.onFailure("로그인 실패")
                }

            }
        })
    }

    override fun modify() {

    }


    companion object {

        private var instance: UserRemoteRemoteDataSourceSourceImpl? = null

        fun getInstance(userApi: UserApi): UserRemoteRemoteDataSourceSourceImpl =
            instance
                ?: instance
                ?: UserRemoteRemoteDataSourceSourceImpl(
                    userApi
                ).also {
                instance = it
            }

    }


}