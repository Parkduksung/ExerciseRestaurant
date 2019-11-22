package com.work.restaurant.ext

import com.work.restaurant.data.model.FitnessCenterItem
import retrofit2.Call
import retrofit2.http.GET

interface FitnessCenterApi {
    @GET("/FitnessCenter.php")
    fun FitnessCenterAllItem(): Call<List<FitnessCenterItem>>
}