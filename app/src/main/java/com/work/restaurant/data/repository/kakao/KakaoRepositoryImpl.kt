package com.work.restaurant.data.repository.kakao

import com.work.restaurant.data.source.remote.kakao.KakaoRemoteDataSource
import com.work.restaurant.data.source.remote.kakao.KakaoRemoteDataSourceCallback
import com.work.restaurant.ext.isConnectedToNetwork
import com.work.restaurant.network.model.kakaoAddress.KakaoAddressDocument
import com.work.restaurant.network.model.kakaoLocationToAddress.KakaoLocationToAddressDocument
import com.work.restaurant.network.model.kakaoSearch.KakaoSearchDocuments
import com.work.restaurant.network.model.kakaoSearch.KakaoSearchResponse
import com.work.restaurant.util.App

class KakaoRepositoryImpl private constructor(
    private val kakaoRemoteDataSource: KakaoRemoteDataSource
) : KakaoRepository {


    override fun getKakaoLocationToAddress(
        currentX: Double,
        currentY: Double,
        callback: KakaoRepositoryCallback.KakaoLocationToAddress
    ) {
        kakaoRemoteDataSource.getKakaoLocationToAddress(
            currentX,
            currentY,
            object : KakaoRemoteDataSourceCallback.KakaoLocationToAddress {
                override fun onSuccess(item: List<KakaoLocationToAddressDocument>) {
                    callback.onSuccess(item)
                }

                override fun onFailure(message: String) {
                    callback.onFailure(message)
                }
            })
    }

    override fun getKakaoAddressLocation(
        addressName: String,
        callback: KakaoRepositoryCallback.KakaoAddressCallback
    ) {
        kakaoRemoteDataSource.getKakaoAddressLocation(
            addressName,
            object : KakaoRemoteDataSourceCallback.KakaoAddressCallback {
                override fun onSuccess(item: List<KakaoAddressDocument>) {
                    callback.onSuccess(item)
                }

                override fun onFailure(message: String) {
                    callback.onFailure(message)
                }
            })
    }


    override fun getKakaoItemInfo(
        placeName: String,
        callback: KakaoRepositoryCallback.KakaoItemInfoCallback
    ) {
        kakaoRemoteDataSource.getKakaoItemInfo(placeName,
            object : KakaoRemoteDataSourceCallback.KakaoItemInfoCallback {
                override fun onSuccess(item: List<KakaoSearchDocuments>) {
                    callback.onSuccess(item)
                }

                override fun onFailure(message: String) {

                    callback.onFailure(message)
                }
            })

    }

    override fun getKakaoResult(
        currentX: Double,
        currentY: Double,
        page: Int,
        sort: String,
        callback: KakaoRepositoryCallback
    ) {
        if (App.instance.context().isConnectedToNetwork()) {
            kakaoRemoteDataSource.getData(
                currentX,
                currentY,
                page,
                sort,
                object : KakaoRemoteDataSourceCallback {
                    override fun onSuccess(
                        kakaoList: KakaoSearchResponse
                    ) {
                        callback.onSuccess(kakaoList)
                    }

                    override fun onFailure(message: String) {
                        callback.onFailure(message)
                    }
                })
        }
    }

    override fun getSearchKakaoList(
        searchName: String,
        page: Int,
        callback: KakaoRepositoryCallback
    ) {
        if (App.instance.context().isConnectedToNetwork()) {
            kakaoRemoteDataSource.getSearchKakaoList(
                searchName,
                page,
                object : KakaoRemoteDataSourceCallback {
                    override fun onSuccess(
                        kakaoList: KakaoSearchResponse
                    ) {
                        callback.onSuccess(kakaoList)
                    }

                    override fun onFailure(message: String) {
                        callback.onFailure(message)
                    }
                })
        }
    }


    companion object {

        private var instance: KakaoRepositoryImpl? = null
        fun getInstance(
            kakaoRemoteDataSource: KakaoRemoteDataSource
        ): KakaoRepositoryImpl =
            instance ?: KakaoRepositoryImpl(kakaoRemoteDataSource).also {
                instance = it
            }

    }

}