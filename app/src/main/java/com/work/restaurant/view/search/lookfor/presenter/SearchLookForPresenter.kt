package com.work.restaurant.view.search.lookfor.presenter

import com.work.restaurant.data.repository.kakao.KakaoRepository
import com.work.restaurant.data.repository.kakao.KakaoRepositoryCallback
import com.work.restaurant.network.model.kakaoSearch.KakaoSearchDocuments

class SearchLookForPresenter(
    private val searchLookForView: SearchLookForContract.View,
    private val kakaoRepository: KakaoRepository
) :
    SearchLookForContract.Presenter {
    override fun backPage() {
        searchLookForView.showBackPage()
    }

    override fun searchLook(searchItem: String) {

        kakaoRepository.getKakaoItemInfo(searchItem,
            object : KakaoRepositoryCallback.KakaoItemInfoCallback {
                override fun onSuccess(item: List<KakaoSearchDocuments>) {
                    val toKakaoSearchModel = item.map {
                        it.toKakaoModel()
                    }
                    searchLookForView.showSearchLook(toKakaoSearchModel)
                }

                override fun onFailure(message: String) {
                    searchLookForView.showSearchNoFind()
                }
            })

    }
}