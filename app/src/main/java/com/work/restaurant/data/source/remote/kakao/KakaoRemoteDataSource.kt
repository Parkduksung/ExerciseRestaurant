package com.work.restaurant.data.source.remote.kakao

interface KakaoRemoteDataSource {

    fun getData(
        currentX: Double,
        currentY: Double,
        page: Int,
        sort: String,
        radius: Int,
        callback: KakaoRemoteDataSourceCallback
    )

    fun getKakaoItemInfo(
        x: Double, y: Double,
        placeName: String,
        callback: KakaoRemoteDataSourceCallback.KakaoItemInfoCallback
    )

    fun getKakaoAddressLocation(
        addressName: String,
        callback: KakaoRemoteDataSourceCallback.KakaoAddressCallback

    )

    fun getKakaoLocationToAddress(
        currentX: Double,
        currentY: Double,
        callback: KakaoRemoteDataSourceCallback.KakaoLocationToAddress
    )


    fun getSearchKakaoList(
        searchName: String,
        page: Int,
        callback: KakaoRemoteDataSourceCallback
    )


}