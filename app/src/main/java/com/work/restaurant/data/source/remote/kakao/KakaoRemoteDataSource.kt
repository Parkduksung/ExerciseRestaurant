package com.work.restaurant.data.source.remote.kakao

interface KakaoRemoteDataSource {

    fun getData(
        currentX: Double,
        currentY: Double,
        callback: KakaoRemoteDataSourceCallback
    )

    fun getKakaoItemInfo(
        placeName: String,
        callback: KakaoRemoteDataSourceCallback.KakaoItemInfoCallback
    )

    fun getKakaoImage(
        placeName: String,
        callback: KakaoRemoteDataSourceCallback.KakaoImageCallback
    )


}