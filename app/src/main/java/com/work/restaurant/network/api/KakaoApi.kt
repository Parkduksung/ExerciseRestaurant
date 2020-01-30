package com.work.restaurant.network.api

import com.work.restaurant.network.model.kakaoImage.KakaoImageResponse
import com.work.restaurant.network.model.kakaoSearch.KakaoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface KakaoApi {
    @Headers(HEADERS)
    @GET("v2/local/search/keyword.json")
    fun keywordSearch(
        @Query("query") keyword: String,
        @Query("x") x: Double,
        @Query("y") y: Double,
        @Query("radius") radius: Int = 20000,
        @Query("sort") sort: String = "accuracy"
    ): Call<KakaoResponse>

    @Headers(HEADERS)
    @GET("v2/search/image.json")
    fun kakaoItemImage(
        @Query("query") keyword: String,
        @Query("sort") sort: String = "accuracy"
    ): Call<KakaoImageResponse>

    @Headers(HEADERS)
    @GET("v2/local/search/keyword.json")
    fun kakaoItemSearcy(
        @Query("query") keyword: String
    ): Call<KakaoResponse>


    companion object {
        const val HEADERS = "Authorization: KakaoAK 785b8c0fb5d7046009351ac6fe2fed8b"
    }


}