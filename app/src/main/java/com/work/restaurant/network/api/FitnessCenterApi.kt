package com.work.restaurant.network.api

import com.work.restaurant.network.model.FitnessCenterItemModel
import retrofit2.Call
import retrofit2.http.GET

interface FitnessCenterApi {
    @GET("/FitnessCenter.php")
    fun FitnessCenterAllItem(): Call<List<FitnessCenterItemModel>>
}