package com.work.restaurant.data.source.remote.kakao

interface KakaoRemoteDataSource {

    fun getData(
        currentX: Double,
        currentY: Double,
        callback: KakaoRemoteDataSourceCallback
    )
}