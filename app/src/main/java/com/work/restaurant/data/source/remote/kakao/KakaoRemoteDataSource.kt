package com.work.restaurant.data.source.remote.kakao

interface KakaoRemoteDataSource {

    fun getData(callback : KakaoRemoteDataSourceCallback)
}