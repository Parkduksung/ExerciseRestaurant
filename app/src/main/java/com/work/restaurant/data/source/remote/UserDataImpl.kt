package com.work.restaurant.data.source.remote

import android.util.Log
import com.work.restaurant.network.api.UserApi
import com.work.restaurant.network.model.ResultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDataImpl private constructor(private val userApi: UserApi) : UserData {

    override fun login(email: String, pass: String, callback: UserDataCallback) {


        userApi.login(email, pass).enqueue(object :
            Callback<ResultResponse> {
            override fun onFailure(call: Call<ResultResponse>?, t: Throwable?) {
                callback.onFailure(t.toString())
            }

            override fun onResponse(
                call: Call<ResultResponse>?,
                response: Response<ResultResponse>?
            ) {


//                response?.body()?.toUser()?.userEmail


                val result = response?.body()?.result

                val resultNickname = response?.body()?.resultNickname

                if (result != null && resultNickname != null) {
                    if (result) {
                        callback.onSuccess(resultNickname)
                    } else {
                        callback.onFailure("등록된 아이디가 없습니다.")
                    }
                } else {
                    callback.onFailure("로그인 실패")
                }

            }
        })

    }

    override fun register(
        nickName: String,
        email: String,
        pass: String,
        callback: UserDataCallback
    ) {


        userApi.register(
            nickName,
            email,
            pass
        ).enqueue(object : Callback<ResultResponse> {
            override fun onFailure(call: Call<ResultResponse>?, t: Throwable?) {
                callback.onFailure(t.toString())
            }

            override fun onResponse(
                call: Call<ResultResponse>?,
                response: Response<ResultResponse>?
            ) {
                val result = response?.body()?.result


                if (result == true) {
                    Log.d("1111111", "성공")
                    callback.onSuccess(nickName)
                }
            }
        })


    }

    override fun delete(userNickname: String, callback: UserDataCallback) {

        userApi.delete(userNickname).enqueue(object :
            Callback<ResultResponse> {
            override fun onFailure(call: Call<ResultResponse>?, t: Throwable?) {
                callback.onFailure(t.toString())
            }

            override fun onResponse(
                call: Call<ResultResponse>?,
                response: Response<ResultResponse>?
            ) {

                val result = response?.body()?.result

                if (result != null) {
                    if (result) {
                        callback.onSuccess(userNickname)
                    } else {
                        callback.onFailure("삭제 실패")
                    }
                } else {
                    callback.onFailure("로그인 실패")
                }

            }
        })
    }

    override fun modify() {

    }


    companion object {

        private var instance: UserDataImpl? = null

        fun getInstance(userApi: UserApi): UserDataImpl =
            instance ?: instance ?: UserDataImpl(userApi).also {
                instance = it
            }

    }


}