package com.work.restaurant.network.api

import com.work.restaurant.network.model.kakaoAddress.KakaoAddressSearch
import com.work.restaurant.network.model.kakaoLocationToAddress.KakaoLocationToAddressResponse
import com.work.restaurant.network.model.kakaoSearch.KakaoSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface KakaoApi {
    @Headers(HEADERS)
    @GET("v2/local/search/keyword.json")
    fun keywordSearch(
        @Query("x") x: Double,
        @Query("y") y: Double,
        @Query("page") page: Int,
        @Query("sort") sort: String,
        @Query("radius") radius: Int = RADIUS,
        @Query("query") keyword: String = KEYWORD
    ): Call<KakaoSearchResponse>


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

    @Headers(HEADERS)
    @GET("v2/local/geo/coord2regioncode.json")
    fun kakaoLocationToAddress(
        @Query("x") x: Double,
        @Query("y") y: Double
    ): Call<KakaoLocationToAddressResponse>


    companion object {
        const val HEADERS = "Authorization: KakaoAK 785b8c0fb5d7046009351ac6fe2fed8b"
        const val RADIUS = 20000
        const val KEYWORD = "헬스장"

    }


}