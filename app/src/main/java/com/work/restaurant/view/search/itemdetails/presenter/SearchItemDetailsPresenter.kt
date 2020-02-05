package com.work.restaurant.view.search.itemdetails.presenter

import android.util.Log
import com.work.restaurant.data.repository.kakao.KakaoRepository
import com.work.restaurant.data.repository.kakao.KakaoRepositoryCallback
import com.work.restaurant.network.model.kakaoSearch.KakaoSearchDocuments

class SearchItemDetailsPresenter(
    private val searchItemDetailsView: SearchItemDetailsContract.View,
    private val kakaoRepository: KakaoRepository
) :
    SearchItemDetailsContract.Presenter {

    override fun kakaoItemInfoDetail(searchItem: String) {

        kakaoRepository.getKakaoItemInfo(
            searchItem,
            object : KakaoRepositoryCallback.KakaoItemInfoCallback {

                override fun onSuccess(item: List<KakaoSearchDocuments>) {

                    val toKakaoSearchModel = item.map{
                        it.toKakaoModel()
                    }

                    searchItemDetailsView.showKakaoItemInfoDetail(toKakaoSearchModel)
                }

                override fun onFailure(message: String) {
                    Log.d("error", message)
                }
            })
    }


}