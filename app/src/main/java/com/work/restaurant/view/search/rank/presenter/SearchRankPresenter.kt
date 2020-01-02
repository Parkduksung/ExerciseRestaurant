package com.work.restaurant.view.search.rank.presenter

import android.util.Log
import com.work.restaurant.data.repository.fitness.FitnessItemRepository
import com.work.restaurant.data.repository.fitness.FitnessItemRepositoryCallback
import com.work.restaurant.network.model.FitnessCenterItemResponse

class SearchRankPresenter(
    private val searchRankView: SearchRankContract.View,
    private val fitnessItemRepository: FitnessItemRepository
) :
    SearchRankContract.Presenter {
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