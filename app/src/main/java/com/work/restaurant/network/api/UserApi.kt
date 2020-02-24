package com.work.restaurant.network.api

import com.work.restaurant.network.model.ResultResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApi {

    @GET("/login.php")
    fun login(
        @Query("userEmail") userEmail: String,
        @Query("userPass") userPass: String
    ): Call<ResultResponse>


    @POST("/register.php")
    fun register(
        @Query("userNickname") userNickname: String,
        @Query("userEmail") userEmail: String,
        @Query("userPass") userPass: String
    ): Call<ResultResponse>


    @GET("/delete.php")
    fun delete(
        @Query("userNickname") userNickname: String,
        @Query("userEmail") userEmail: String
    ): Call<ResultResponse>


    @GET("/find.php")
    fun find(
        @Query("userEmail") userEmail: String
    ): Call<ResultResponse>


    @GET("/update.php")
    fun update(
        @Query("userEmail") userEmail: String,
        @Query("userPass") userPass: String
    ): Call<ResultResponse>


}