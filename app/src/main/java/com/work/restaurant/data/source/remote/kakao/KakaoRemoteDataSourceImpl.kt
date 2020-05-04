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
        x: Double, y: Double,
        placeName: String,
        callback: KakaoRemoteDataSourceCallback.KakaoItemInfoCallback
    ) {
        kakaoApi.kakaoItemSearch(x, y, placeName).enqueue(object : Callback<KakaoSearchResponse> {
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
                            if (it.categoryName.contains(SEARCH_CATEGORY)) {
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
        radius: Int,
        callback: KakaoRemoteDataSourceCallback
    ) {
        kakaoApi.keywordSearch(currentX, currentY, page, sort, radius).enqueue(object :
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

                        val toSortDocuments = mutableListOf<KakaoSearchDocuments>()

                        response.body().documents.forEach {
                            if (it.categoryName.contains(SEARCH_CATEGORY)) {
                                toSortDocuments.add(it)
                            }
                        }
                        val kakaoList =
                            KakaoSearchResponse(toSortDocuments, response.body().kakaoSearchMeta)

                        callback.onSuccess(
                            kakaoList
                        )
                    } else {
                        callback.onFailure(response.message())
                    }
                }
            }
        })
    }

    override fun getSearchKakaoList(
        searchName: String,
        page: Int,
        callback: KakaoRemoteDataSourceCallback
    ) {

        kakaoApi.keywordLookForSearch(searchName, page).enqueue(object :
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

        private const val SEARCH_CATEGORY = "스포츠,레저 > 스포츠시설 > 헬스클럽"

        fun getInstance(kakaoApi: KakaoApi): KakaoRemoteDataSourceImpl =
            instance ?: KakaoRemoteDataSourceImpl(kakaoApi).also {
                instance = it
            }


    }
}
