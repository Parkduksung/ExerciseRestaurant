package com.work.restaurant.data.repository.kakao

import com.work.restaurant.network.model.kakaoAddress.KakaoAddressDocument
import com.work.restaurant.network.model.kakaoImage.KakaoImageDocuments
import com.work.restaurant.network.model.kakaoSearch.KakaoSearchDocuments

interface KakaoRepositoryCallback {

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

    interface KakaoAddressCallback {

        fun onSuccess(item: List<KakaoAddressDocument>)

        fun onFailure(message: String)

    }


}