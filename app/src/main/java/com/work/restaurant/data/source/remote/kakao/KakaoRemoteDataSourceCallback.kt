package com.work.restaurant.data.source.remote.kakao

import com.work.restaurant.network.model.Documents

interface KakaoRemoteDataSourceCallback {

    fun onSuccess(fitnessList: List<Documents>)

    fun onFailure(message: String)
}