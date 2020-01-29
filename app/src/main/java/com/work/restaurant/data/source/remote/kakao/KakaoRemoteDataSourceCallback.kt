package com.work.restaurant.data.source.remote.kakao

import com.work.restaurant.network.model.kakao.Documents

interface KakaoRemoteDataSourceCallback {

    fun onSuccess(kakaoList: List<Documents>)

    fun onFailure(message: String)
}