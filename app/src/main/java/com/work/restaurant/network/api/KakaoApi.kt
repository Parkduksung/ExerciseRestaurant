package com.work.restaurant.network.api

import com.work.restaurant.network.model.kakaoAddress.KakaoAddressSearch
import com.work.restaurant.network.model.kakaoImage.KakaoImageResponse
import com.work.restaurant.network.model.kakaoSearch.KakaoSearchResponse
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
    ): Call<KakaoSearchResponse>

    @Headers(HEADERS)
    @GET("v2/search/image.json")
    fun kakaoItemImage(
        @Query("query") keyword: String,
        @Query("sort") sort: String = "accuracy"
    ): Call<KakaoImageResponse>

    @Headers(HEADERS)
    @GET("v2/local/search/keyword.json")
    fun kakaoItemSearch(
        @Query("query") keyword: String
    ): Call<KakaoSearchResponse>

    @Headers(HEADERS)
    @GET("v2/local/search/address.json")
    fun kakaoAddressSearch(
        @Query("query") keyword: String
    ): Call<KakaoAddressSearch>


    companion object {
        const val HEADERS = "Authorization: KakaoAK 785b8c0fb5d7046009351ac6fe2fed8b"
    }


}