package com.work.restaurant.data.source.remote.user

import com.google.firebase.auth.FirebaseAuth
import com.work.restaurant.network.api.UserApi
import com.work.restaurant.network.model.ResultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRemoteRemoteDataSourceSourceImpl private constructor(private val userApi: UserApi) :
    UserRemoteDataSource {

    private val firebaseAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun login(
        email: String,
        pass: String,
        callbackRemoteSource: UserRemoteDataSourceCallback
    ) {

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

                        firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                            if (it.isSuccessful) {
                                callbackRemoteSource.onSuccess(resultNickname)
                            } else {
                                callbackRemoteSource.onFailure("로그인 실패")
                            }
                        }

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
                        firebaseAuth.createUserWithEmailAndPassword(email, pass)
                            .addOnCompleteListener { Task ->
                                if (Task.isSuccessful) {
                                    callbackRemoteSource.onSuccess(nickName)
                                } else {
                                    callbackRemoteSource.onFailure("실패")
                                }
                            }

                    }

                }

            }
        })


    }

    override fun delete(
        userNickname: String,
        userEmail: String,
        callbackRemoteSource: UserRemoteDataSourceCallback
    ) {

        userApi.delete(userNickname, userEmail).enqueue(object :
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

                        firebaseAuth.currentUser?.delete()?.addOnCompleteListener {
                            if (it.isSuccessful) {
                                callbackRemoteSource.onSuccess(userNickname)
                            } else {
                                callbackRemoteSource.onFailure("삭제 실패")
                            }
                        }


                    } else {
                        callbackRemoteSource.onFailure("삭제 실패")
                    }
                } else {
                    callbackRemoteSource.onFailure("로그인 실패")
                }

            }
        })
    }


    override fun resetPass(email: String, callbackRemoteSource: UserRemoteDataSourceCallback) {

        userApi.find(email).enqueue(object : Callback<ResultResponse> {

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
                        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener {
                            if (it.isSuccessful) {
                                callbackRemoteSource.onSuccess(resultNickname)
                            } else {
                                callbackRemoteSource.onFailure("비밀번호 초기화 실패.")
                            }
                        }
                    } else {
                        callbackRemoteSource.onFailure("등록된 아이디가 없습니다.")
                    }
                } else {
                    callbackRemoteSource.onFailure("비밀번호 초기화 실패.")
                }

            }
        })
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