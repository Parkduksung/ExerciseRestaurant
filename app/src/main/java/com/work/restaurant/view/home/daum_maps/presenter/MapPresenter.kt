package com.work.restaurant.view.home.daum_maps.presenter

import android.util.Log
import com.work.restaurant.data.repository.kakao.KakaoRepository
import com.work.restaurant.data.repository.kakao.KakaoRepositoryCallback
import com.work.restaurant.network.model.kakaoSearch.KakaoSearchDocuments

class MapPresenter(
    private val mapView: MapContract.View,
    private val kakaoRepository: KakaoRepository
) : MapContract.Presenter {
    override fun getMarkerData(markerName: String) {
        kakaoRepository.getKakaoItemInfo(markerName,
            object : KakaoRepositoryCallback.KakaoItemInfoCallback {
                override fun onSuccess(item: List<KakaoSearchDocuments>) {
                    val toKakaoSearchModel = item.map { it.toKakaoModel() }

                    if (toKakaoSearchModel.isNotEmpty()) {
                        mapView.showMarkerData(toKakaoSearchModel)
                    }
                }

                override fun onFailure(message: String) {

                }
            })
    }

    override fun getKakaoData(currentX: Double, currentY: Double) {
        kakaoRepository.getKakaoResult(
            currentX,
            currentY,
            object : KakaoRepositoryCallback {
                override fun onSuccess(kakaoList: List<KakaoSearchDocuments>) {

                    val toKakaoModelList = kakaoList.map { it.toKakaoModel() }

                    mapView.showKakaoData(
                        currentX,
                        currentY,
                        toKakaoModelList
                    )
                }

                override fun onFailure(message: String) {
                    Log.d("카카오결과", message)
                }
            }
        )
    }
}