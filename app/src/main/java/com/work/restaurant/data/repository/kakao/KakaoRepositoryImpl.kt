package com.work.restaurant.data.repository.kakao

import android.util.Log
import com.work.restaurant.data.source.remote.kakao.KakaoRemoteDataSource
import com.work.restaurant.data.source.remote.kakao.KakaoRemoteDataSourceCallback
import com.work.restaurant.ext.isConnectedToNetwork
import com.work.restaurant.network.model.kakaoImage.KakaoImageDocuments
import com.work.restaurant.network.model.kakaoSearch.KakaoSearchDocuments
import com.work.restaurant.util.App

class KakaoRepositoryImpl private constructor(
    private val kakaoRemoteDataSource: KakaoRemoteDataSource
) : KakaoRepository {
    override fun getKakaoImage(
        placeName: String,
        callback: KakaoRepositoryCallback.KakaoImageCallback
    ) {
        kakaoRemoteDataSource.getKakaoImage(placeName,
            object : KakaoRemoteDataSourceCallback.KakaoImageCallback {
                override fun onSuccess(item: List<KakaoImageDocuments>) {
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
                override fun onSuccess(item: KakaoSearchDocuments) {

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
        callback: KakaoRepositoryCallback
    ) {
        if (App.instance.context().isConnectedToNetwork()) {
            kakaoRemoteDataSource.getData(
                currentX,
                currentY,
                object : KakaoRemoteDataSourceCallback {
                    override fun onSuccess(kakaoList: List<KakaoSearchDocuments>) {
                        callback.onSuccess(kakaoList)
                        Log.d("카카오결과", "3")
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