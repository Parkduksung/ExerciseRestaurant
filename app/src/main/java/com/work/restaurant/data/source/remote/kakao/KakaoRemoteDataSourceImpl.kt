package com.work.restaurant.data.source.remote.kakao

import com.work.restaurant.network.api.KakaoApi
import com.work.restaurant.network.model.kakaoAddress.KakaoAddressSearch
import com.work.restaurant.network.model.kakaoLocationToAddress.KakaoLocationToAddressResponse
import com.work.restaurant.network.model.kakaoSearch.KakaoSearchDocuments
import com.work.restaurant.network.model.kakaoSearch.KakaoSearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KakaoRemoteDataSourceImpl private constructor(private val kakaoApi: KakaoApi) :
    KakaoRemoteDataSource {

    override fun getKakaoLocationToAddress(
        currentX: Double,
        currentY: Double,
        callback: KakaoRemoteDataSourceCallback.KakaoLocationToAddress
    ) {

        kakaoApi.kakaoLocationToAddress(currentX, currentY)
            .enqueue(object : Callback<KakaoLocationToAddressResponse> {
                override fun onFailure(call: Call<KakaoLocationToAddressResponse>?, t: Throwable?) {
                    callback.onFailure("${t?.message}")
                }

                override fun onResponse(
                    call: Call<KakaoLocationToAddressResponse>?,
                    response: Response<KakaoLocationToAddressResponse>?
                ) {
                    if (response != null) {
                        if (response.isSuccessful) {
                            val list = response.body().documents
                            callback.onSuccess(list)
                        } else {
                            callback.onFailure(response.message())
                        }
                    }
                }
            })
    }

    override fun getKakaoAddressLocation(
        addressName: String,
        callback: KakaoRemoteDataSourceCallback.KakaoAddressCallback
    ) {
        kakaoApi.kakaoAddressSearch(addressName).enqueue(object : Callback<KakaoAddressSearch> {
            override fun onFailure(call: Call<KakaoAddressSearch>?, t: Throwable?) {
                callback.onFailure("${t?.message}")
            }

            override fun onResponse(
                call: Call<KakaoAddressSearch>?,
                response: Response<KakaoAddressSearch>?
            ) {
                if (response != null) {
                    if (response.isSuccessful) {
                        val list = response.body().documents
                        callback.onSuccess(list)
                    } else {
                        callback.onFailure(response.message())
                    }
                }
            }
        })
    }


    override fun getKakaoItemInfo(
        placeName: String,
        callback: KakaoRemoteDataSourceCallback.KakaoItemInfoCallback
    ) {
        kakaoApi.kakaoItemSearch(placeName).enqueue(object : Callback<KakaoSearchResponse> {
            override fun onFailure(call: Call<KakaoSearchResponse>?, t: Throwable?) {
                callback.onFailure("${t?.message}")
            }

            override fun onResponse(
                call: Call<KakaoSearchResponse>?,
                response: Response<KakaoSearchResponse>?
            ) {
                if (response != null) {
                    if (response.isSuccessful) {

                        val toSortDocuments = mutableListOf<KakaoSearchDocuments>()

                        response.body().documents.forEach {
                            if (it.categoryName.contains("스포츠,레저")) {
                                toSortDocuments.add(it)
                            }
                        }

                        callback.onSuccess(toSortDocuments)
                    } else {
                        callback.onFailure(response.message())
                    }
                }
            }
        })
    }

    override fun getData(
        currentX: Double,
        currentY: Double,
        page: Int,
        sort: String,
        callback: KakaoRemoteDataSourceCallback
    ) {
        kakaoApi.keywordSearch(currentX, currentY, page, sort).enqueue(object :
            Callback<KakaoSearchResponse> {
            override fun onFailure(call: Call<KakaoSearchResponse>?, t: Throwable?) {
                callback.onFailure("${t?.message}")
            }

            override fun onResponse(
                call: Call<KakaoSearchResponse>?,
                response: Response<KakaoSearchResponse>?
            ) {
                if (response != null) {
                    if (response.isSuccessful) {
                        callback.onSuccess(
                            response.body()
                        )
                    } else {
                        callback.onFailure(response.message())
                    }
                }
            }
        })
    }


    companion object {

        private var instance: KakaoRemoteDataSourceImpl? = null

        fun getInstance(kakaoApi: KakaoApi): KakaoRemoteDataSourceImpl =
            instance ?: KakaoRemoteDataSourceImpl(kakaoApi).also {
                instance = it
            }


    }
}
