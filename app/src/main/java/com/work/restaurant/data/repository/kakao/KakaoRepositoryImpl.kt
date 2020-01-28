package com.work.restaurant.data.repository.kakao

import android.util.Log
import com.work.restaurant.data.source.remote.kakao.KakaoRemoteDataSource
import com.work.restaurant.data.source.remote.kakao.KakaoRemoteDataSourceCallback
import com.work.restaurant.ext.isConnectedToNetwork
import com.work.restaurant.network.model.Documents
import com.work.restaurant.util.App

class KakaoRepositoryImpl private constructor(
    private val kakaoRemoteDataSource: KakaoRemoteDataSource
) : KakaoRepository {
    override fun getKakaoResult(callback: KakaoRepositoryCallback) {

        if (App.instance.context().isConnectedToNetwork()) {
            kakaoRemoteDataSource.getData(object : KakaoRemoteDataSourceCallback {
                override fun onSuccess(fitnessList: List<Documents>) {
                    callback.onSuccess(fitnessList)
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