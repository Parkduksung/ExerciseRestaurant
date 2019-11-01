package com.work.restaurant.login

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {
    @GET("Login.php")
    fun login(
        @Query("user_email") user_email: String,
        @Query("user_pass") user_pass: String
    ): Call<ResultModel>

}