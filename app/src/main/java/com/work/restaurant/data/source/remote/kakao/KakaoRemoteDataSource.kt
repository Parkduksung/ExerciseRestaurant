package com.work.restaurant.data.source.remote.kakao

import com.work.restaurant.network.model.kakaoAddress.KakaoAddressDocument
import com.work.restaurant.network.model.kakaoLocationToAddress.KakaoLocationToAddressDocument
import com.work.restaurant.network.model.kakaoSearch.KakaoSearchDocuments
import com.work.restaurant.network.model.kakaoSearch.KakaoSearchResponse

interface KakaoRemoteDataSource {

    fun getData(
        currentX: Double,
        currentY: Double,
        page: Int,
        sort: String,
        radius: Int,
        callback: (list: KakaoSearchResponse?) -> Unit
    )

    fun getKakaoItemInfo(
        x: Double, y: Double,
        placeName: String,
        callback: (item: List<KakaoSearchDocuments>?) -> Unit
    )

    fun getKakaoAddressLocation(
        addressName: String,
        callback: (item: List<KakaoAddressDocument>?) -> Unit

    )

    fun getKakaoLocationToAddress(
        currentX: Double,
        currentY: Double,
        callback: (item: List<KakaoLocationToAddressDocument>?) -> Unit
    )


    fun getSearchKakaoList(
        searchName: String,
        page: Int,
        callback: (list: KakaoSearchResponse?) -> Unit
    )


}