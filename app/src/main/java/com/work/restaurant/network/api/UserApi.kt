package com.work.restaurant.network.api

import com.work.restaurant.network.model.ResultResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApi {

    @GET("/login.php")
    fun login(
        @Query("user_email") user_email: String,
        @Query("user_pass") user_pass: String
    ): Call<ResultResponse>
//usermodel로 해서 할것.

    @POST("/register.php")
    fun register(
        @Query("user_nickname") user_nickname: String,
        @Query("user_email") user_email: String,
        @Query("user_pass") user_pass: String
    ): Call<ResultResponse>


    @GET("/delete.php")
    fun delete(
        @Query("user_nickname") user_nickname: String,
        @Query("user_email") user_email: String
    ): Call<ResultResponse>


    @GET("/find.php")
    fun find(
        @Query("user_email") user_email: String
    ): Call<ResultResponse>


}