package com.work.restaurant.view.search.rank.presenter

import android.util.Log
import com.work.restaurant.data.repository.fitness.FitnessItemRepository
import com.work.restaurant.data.repository.fitness.FitnessItemRepositoryCallback
import com.work.restaurant.data.repository.kakao.KakaoRepository
import com.work.restaurant.data.repository.kakao.KakaoRepositoryCallback
import com.work.restaurant.network.model.FitnessCenterItemResponse
import com.work.restaurant.network.model.kakaoSearch.KakaoSearchDocuments

class SearchRankPresenter(
    private val searchRankView: SearchRankContract.View,
    private val fitnessItemRepository: FitnessItemRepository,
    private val kakaoRepository: KakaoRepository
) :
    SearchRankContract.Presenter {
    override fun getKakaoList() {

        kakaoRepository.getKakaoResult(
            126.73694610595703,
            37.538692474365234,
            object : KakaoRepositoryCallback {
                override fun onSuccess(kakaoList: List<KakaoSearchDocuments>) {
                    searchRankView.showKakaoList(kakaoList)
                }

                override fun onFailure(message: String) {
                    Log.d("error", message)
                }
            })


    }

    override fun getFitnessList() {


        fitnessItemRepository.getFitnessResult(object : FitnessItemRepositoryCallback {
            override fun onSuccess(fitnessList: List<FitnessCenterItemResponse>) {
                searchRankView.showFitnessList(fitnessList)
            }

            override fun onFailure(message: String) {
                Log.d("error", message)
            }
        })


    }


    override fun settings() {
        searchRankView.showSettings()
    }
}