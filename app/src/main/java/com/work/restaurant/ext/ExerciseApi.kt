package com.work.restaurant.ext

import com.work.restaurant.data.model.ExerciseRank
import retrofit2.Call
import retrofit2.http.GET

interface ExerciseApi {
    @GET("/Exercise.php")
    fun exerciseAllItem(): Call<List<ExerciseRank>>
}