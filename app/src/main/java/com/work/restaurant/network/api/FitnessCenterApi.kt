package com.work.restaurant.network.api

import com.work.restaurant.network.model.FitnessCenterItemResponse
import retrofit2.Call
import retrofit2.http.GET

interface FitnessCenterApi {
    @GET("/FitnessCenter.php")
    fun fitnessCenterAllItem(): Call<List<FitnessCenterItemResponse>>

}



