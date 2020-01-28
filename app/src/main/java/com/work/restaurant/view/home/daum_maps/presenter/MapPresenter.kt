package com.work.restaurant.view.home.daum_maps.presenter

import android.util.Log
import com.work.restaurant.data.repository.kakao.KakaoRepository
import com.work.restaurant.data.repository.kakao.KakaoRepositoryCallback
import com.work.restaurant.network.model.Documents

class MapPresenter(
    private val mapView: MapContract.View,
    private val kakaoRepository: KakaoRepository
) : MapContract.Presenter {

    override fun getKakaoData() {

        kakaoRepository.getKakaoResult(object : KakaoRepositoryCallback {

            override fun onSuccess(fitnessList: List<Documents>) {
                mapView.showKakaoData(fitnessList)
                Log.d("카카오결과", "2")
            }

            override fun onFailure(message: String) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

    }
}