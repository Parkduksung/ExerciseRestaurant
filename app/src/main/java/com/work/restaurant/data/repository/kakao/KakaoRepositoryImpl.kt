package com.work.restaurant.data.repository.kakao

import com.work.restaurant.data.source.remote.kakao.KakaoRemoteDataSource
import com.work.restaurant.network.model.kakaoAddress.KakaoAddressDocument
import com.work.restaurant.network.model.kakaoLocationToAddress.KakaoLocationToAddressDocument
import com.work.restaurant.network.model.kakaoSearch.KakaoSearchDocuments
import com.work.restaurant.network.model.kakaoSearch.KakaoSearchResponse

class KakaoRepositoryImpl(
    private val kakaoRemoteDataSource: KakaoRemoteDataSource
) : KakaoRepository {
    override fun getData(
        currentX: Double,
        currentY: Double,
        page: Int,
        sort: String,
        radius: Int,
        callback: (list: KakaoSearchResponse?) -> Unit
    ) {
        kakaoRemoteDataSource.getData(currentX, currentY, page, sort, radius, callback)
    }

    override fun getKakaoItemInfo(
        x: Double,
        y: Double,
        placeName: String,
        callback: (item: List<KakaoSearchDocuments>?) -> Unit
    ) {
        kakaoRemoteDataSource.getKakaoItemInfo(x, y, placeName, callback)
    }

    override fun getKakaoAddressLocation(
        addressName: String,
        callback: (item: List<KakaoAddressDocument>?) -> Unit
    ) {
        kakaoRemoteDataSource.getKakaoAddressLocation(addressName, callback)
    }

    override fun getKakaoLocationToAddress(
        currentX: Double,
        currentY: Double,
        callback: (item: List<KakaoLocationToAddressDocument>?) -> Unit
    ) {
        kakaoRemoteDataSource.getKakaoLocationToAddress(currentX, currentY, callback)
    }

    override fun getSearchKakaoList(
        searchName: String,
        page: Int,
        callback: (list: KakaoSearchResponse?) -> Unit
    ) {
        kakaoRemoteDataSource.getSearchKakaoList(searchName, page, callback)
    }

}