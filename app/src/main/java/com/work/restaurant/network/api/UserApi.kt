package com.work.restaurant.network.api

import com.work.restaurant.network.model.ResultModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApi {

    @GET("/Login.php")
    fun login(
        @Query("user_email") user_email: String,
        @Query("user_pass") user_pass: String
    ): Call<ResultModel>


    @POST("/insert.php")
    fun register(
        @Query("user_nickname") user_nickname: String,
        @Query("user_email") user_email: String,
        @Query("user_pass") user_pass: String
    ): Call<ResultModel>


    @GET("/delete.php")
    fun delete(
        @Query("user_email") user_email: String
    ): Call<ResultModel>


    @GET("/find.php")
    fun find(
        @Query("user_email") user_email: String
    ): Call<ResultModel>


}