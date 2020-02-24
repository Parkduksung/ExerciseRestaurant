package com.work.restaurant.data.source.remote.user

import com.google.firebase.auth.FirebaseAuth
import com.work.restaurant.network.api.UserApi
import com.work.restaurant.network.model.ResultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRemoteDataSourceImpl private constructor(private val userApi: UserApi) :
    UserRemoteDataSource {

    private val firebaseAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun login(
        email: String,
        pass: String,
        callbackRemoteSource: UserRemoteDataSourceCallback
    ) {

        firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
            if (it.isSuccessful) {

                userApi.login(email, pass).enqueue(object :
                    Callback<ResultResponse> {
                    override fun onFailure(call: Call<ResultResponse>?, t: Throwable?) {
//
                        userApi.update(email, pass).enqueue(object : Callback<ResultResponse> {
                            override fun onFailure(
                                call: Call<ResultResponse>?,
                                t: Throwable?
                            ) {
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
                                        callbackRemoteSource.onFailure("쿼리실패...")
                                    }
                                }
                            }
                        })
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
                                callbackRemoteSource.onFailure("쿼리실패...")
                            }
                        }

                    }
                })

            } else {
                callbackRemoteSource.onFailure(it.exception.toString())
            }
        }


    }

    override fun register(
        nickName: String,
        email: String,
        pass: String,
        callbackRemoteSource: UserRemoteDataSourceCallback
    ) {

        firebaseAuth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener { Task ->
                if (Task.isSuccessful) {
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
                                    callbackRemoteSource.onSuccess(nickName)
                                } else {
                                    callbackRemoteSource.onFailure(response.message())
                                }
                            }
                        }
                    })
                } else {
                    callbackRemoteSource.onFailure(Task.exception.toString())
                }
            }


    }

    override fun delete(
        userNickname: String,
        userEmail: String,
        callbackRemoteSource: UserRemoteDataSourceCallback
    ) {

        firebaseAuth.currentUser?.delete()?.addOnCompleteListener {
            if (it.isSuccessful) {
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
                                callbackRemoteSource.onSuccess(userNickname)
                            } else {
                                callbackRemoteSource.onFailure("삭제 실패")
                            }
                        }
                    }
                })
            } else {
                callbackRemoteSource.onFailure(it.exception.toString())
            }
        }

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

        private var instance: UserRemoteDataSourceImpl? = null

        fun getInstance(userApi: UserApi): UserRemoteDataSourceImpl =
            instance ?: UserRemoteDataSourceImpl(userApi).also {
                instance = it
            }

    }


}