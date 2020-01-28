package com.work.restaurant.network.api

import com.work.restaurant.network.model.KakaoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface KakaoApi {
    @Headers("Authorization: KakaoAK 785b8c0fb5d7046009351ac6fe2fed8b")
    @GET("v2/local/search/keyword.json?y=37.514322572335935&x=127.06283102249932&radius=20000")
    fun keywordSearch(@Query("keyword") keyword: String): Call<KakaoResponse>

}