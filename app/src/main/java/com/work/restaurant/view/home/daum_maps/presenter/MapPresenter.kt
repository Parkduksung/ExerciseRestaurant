package com.work.restaurant.view.home.daum_maps.presenter

import com.work.restaurant.data.repository.kakao.KakaoRepository
import com.work.restaurant.data.repository.kakao.KakaoRepositoryCallback
import com.work.restaurant.network.model.kakaoSearch.KakaoSearchDocuments
import com.work.restaurant.network.model.kakaoSearch.KakaoSearchResponse

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
            PAGENUM,
            SORT_DISTANCE,
            object : KakaoRepositoryCallback {
                override fun onSuccess(kakaoList: KakaoSearchResponse) {
                    val toKakaoModelList = kakaoList.documents.map { it.toKakaoModel() }
                    mapView.showKakaoData(
                        toKakaoModelList
                    )
                }

                override fun onFailure(message: String) {
                }
            }
        )
    }


    companion object {
        private const val PAGENUM = 1
        private const val SORT_DISTANCE = "distance"
    }
}