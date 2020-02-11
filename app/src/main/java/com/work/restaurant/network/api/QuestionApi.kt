package com.work.restaurant.network.api

import com.work.restaurant.network.model.ResultResponse
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface QuestionApi {
    @POST("/Question.php")
    fun register(
        @Query("questionContent") questionContent: String
    ): Call<ResultResponse>
}