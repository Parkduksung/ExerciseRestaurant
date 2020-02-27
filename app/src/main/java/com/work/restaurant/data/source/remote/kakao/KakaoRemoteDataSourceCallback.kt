package com.work.restaurant.data.source.remote.kakao

import com.work.restaurant.network.model.kakaoAddress.KakaoAddressDocument
import com.work.restaurant.network.model.kakaoLocationToAddress.KakaoLocationToAddressDocument
import com.work.restaurant.network.model.kakaoSearch.KakaoSearchDocuments
import com.work.restaurant.network.model.kakaoSearch.KakaoSearchResponse

interface KakaoRemoteDataSourceCallback {

    fun onSuccess(kakaoList: KakaoSearchResponse)

    fun onFailure(message: String)


    interface KakaoItemInfoCallback {

        fun onSuccess(item: List<KakaoSearchDocuments>)

        fun onFailure(message: String)
    }


    interface KakaoAddressCallback {
        fun onSuccess(item: List<KakaoAddressDocument>)
        fun onFailure(message: String)
    }


    interface KakaoLocationToAddress {
        fun onSuccess(item: List<KakaoLocationToAddressDocument>)
        fun onFailure(message: String)
    }

}