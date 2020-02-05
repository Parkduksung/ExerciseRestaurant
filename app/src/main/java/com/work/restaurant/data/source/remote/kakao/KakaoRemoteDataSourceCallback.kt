package com.work.restaurant.data.source.remote.kakao

import com.work.restaurant.network.model.kakaoImage.KakaoImageDocuments
import com.work.restaurant.network.model.kakaoSearch.KakaoSearchDocuments

interface KakaoRemoteDataSourceCallback {

    fun onSuccess(kakaoList: List<KakaoSearchDocuments>)

    fun onFailure(message: String)


    interface KakaoItemInfoCallback {

        fun onSuccess(item: List<KakaoSearchDocuments>)

        fun onFailure(message: String)
    }

    interface KakaoImageCallback {
        fun onSuccess(item: List<KakaoImageDocuments>)

        fun onFailure(message: String)
    }

}