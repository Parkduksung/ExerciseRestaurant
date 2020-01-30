package com.work.restaurant.view.search.itemdetails.presenter

import android.util.Log
import com.work.restaurant.data.repository.fitness.FitnessItemRepository
import com.work.restaurant.data.repository.fitness.FitnessItemRepositoryCallback
import com.work.restaurant.data.repository.kakao.KakaoRepository
import com.work.restaurant.data.repository.kakao.KakaoRepositoryCallback
import com.work.restaurant.network.model.FitnessCenterItemResponse
import com.work.restaurant.network.model.kakaoImage.KakaoImageDocuments
import com.work.restaurant.network.model.kakaoSearch.KakaoSearchDocuments

class SearchItemDetailsPresenter(
    private val searchItemDetailsView: SearchItemDetailsContract.View,
    private val fitnessItemRepository: FitnessItemRepository,
    private val kakaoRepository: KakaoRepository
) :
    SearchItemDetailsContract.Presenter {
    override fun kakaoItemImage(searchItem: String) {

        kakaoRepository.getKakaoImage(
            searchItem,
            object : KakaoRepositoryCallback.KakaoImageCallback {
                override fun onSuccess(item: List<KakaoImageDocuments>) {
                    searchItemDetailsView.showKakaoImage(item)
                }

                override fun onFailure(message: String) {
                    Log.d("error", message)
                }
            })


    }

    override fun kakaoItemInfoDetail(searchItem: String) {

        kakaoRepository.getKakaoItemInfo(
            searchItem,
            object : KakaoRepositoryCallback.KakaoItemInfoCallback {

                override fun onSuccess(item: KakaoSearchDocuments) {

                    searchItemDetailsView.showKakaoItemInfoDetail(item)
                }

                override fun onFailure(message: String) {
                    Log.d("error", message)
                }
            })
    }

    override fun call(callNum: String) {
        searchItemDetailsView.showCall(callNum)
    }

    override fun itemInfoDetail(searchItem: String) {

        fitnessItemRepository.getFitnessResult(object : FitnessItemRepositoryCallback {
            override fun onSuccess(fitnessList: List<FitnessCenterItemResponse>) {


                fitnessList.forEach { fitnessCenterItemModel ->
                    if (fitnessCenterItemModel.fitnessCenterName == searchItem) {
                        searchItemDetailsView.showItemInfoDetail(fitnessCenterItemModel)
                    }
                }

            }

            override fun onFailure(message: String) {
                Log.d("error", message)
            }
        })


    }


}