package com.work.restaurant.data.source.remote.user

import com.google.firebase.auth.FirebaseAuth
import com.work.restaurant.network.api.UserApi
import com.work.restaurant.network.model.ResultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRemoteDataSourceImpl(private val userApi: UserApi) :
    UserRemoteDataSource {

    private val firebaseAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun login(email: String, pass: String, callback: (resultNickname: String?) -> Unit) {

        firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
            if (it.isSuccessful) {

                userApi.login(email, pass).enqueue(object :
                    Callback<ResultResponse> {
                    override fun onFailure(call: Call<ResultResponse>?, t: Throwable?) {

                        userApi.update(email, pass).enqueue(object : Callback<ResultResponse> {
                            override fun onFailure(
                                call: Call<ResultResponse>?,
                                t: Throwable?
                            ) {
                                callback(null)
                            }

                            override fun onResponse(
                                call: Call<ResultResponse>?,
                                response: Response<ResultResponse>?
                            ) {
                                val result =
                                    response?.body()?.result
                                val resultNickname =
                                    response?.body()?.resultNickname

                                if (result != null && resultNickname != null) {
                                    if (result) {
                                        callback(resultNickname)
                                    } else {
                                        callback(null)
                                    }
                                }
                            }
                        })
                    }

                    override fun onResponse(
                        call: Call<ResultResponse>?,
                        response: Response<ResultResponse>?
                    ) {
                        val result =
                            response?.body()?.result
                        val resultNickname =
                            response?.body()?.resultNickname

                        if (result != null && resultNickname != null) {
                            if (result) {
                                callback(resultNickname)
                            } else {
                                callback(null)
                            }
                        }

                    }
                })

            } else {
                callback(null)
            }
        }
    }

    override fun register(
        nickName: String,
        email: String,
        pass: String,
        callback: (resultNickname: String?) -> Unit
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
                            callback(null)
                        }

                        override fun onResponse(
                            call: Call<ResultResponse>?,
                            response: Response<ResultResponse>?
                        ) {
                            val result =
                                response?.body()?.result

                            result?.let {
                                if (it) {
                                    callback(nickName)
                                } else {
                                    callback(null)
                                }
                            }
                        }
                    })
                } else {
                    callback(null)
                }
            }
    }

    override fun delete(
        userNickname: String,
        userEmail: String,
        callback: (resultNickname: String?) -> Unit
    ) {
        firebaseAuth.currentUser?.delete()?.addOnCompleteListener {
            if (it.isSuccessful) {
                userApi.delete(userNickname, userEmail).enqueue(object :
                    Callback<ResultResponse> {
                    override fun onFailure(call: Call<ResultResponse>?, t: Throwable?) {
                        callback(null)
                    }

                    override fun onResponse(
                        call: Call<ResultResponse>?,
                        response: Response<ResultResponse>?
                    ) {
                        val result =
                            response?.body()?.result
                        if (result != null) {
                            if (result) {
                                callback(userNickname)
                            } else {
                                callback(null)
                            }
                        }
                    }
                })
            } else {
                callback(null)
            }
        }
    }

    override fun resetPass(email: String, callback: (resultNickname: String?) -> Unit) {
        userApi.find(email).enqueue(object : Callback<ResultResponse> {

            override fun onFailure(call: Call<ResultResponse>?, t: Throwable?) {
                callback(null)
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
                                callback(resultNickname)
                            } else {
                                callback(null)
                            }
                        }
                    } else {
                        callback(null)
                    }
                } else {
                    callback(null)
                }

            }
        })
    }

    override fun emailDuplicationCheck(email: String, callback: (isSuccess: Boolean) -> Unit) {
        userApi.emailDuplicationCheck(email).enqueue(object : Callback<ResultResponse> {
            override fun onFailure(call: Call<ResultResponse>?, t: Throwable?) {
                callback(false)
            }

            override fun onResponse(
                call: Call<ResultResponse>?,
                response: Response<ResultResponse>?
            ) {
                val result =
                    response?.body()?.result

                if (result != null) {
                    if (!result) {
                        callback(true)
                    } else {
                        callback(false)
                    }
                }
            }
        })
    }
}