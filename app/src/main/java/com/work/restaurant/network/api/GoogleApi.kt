package com.work.restaurant.network.api


import com.work.restaurant.network.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface GoogleApi {

    @GET
    fun getNearbyPlaces(@Url url: String): Call<PlaceResponse>
}