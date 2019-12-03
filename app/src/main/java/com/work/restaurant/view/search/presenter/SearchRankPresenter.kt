package com.work.restaurant.view.search.presenter

import android.util.Log
import com.work.restaurant.data.repository.search.FitnessItemRepositoryCallback
import com.work.restaurant.data.repository.search.FitnessItemRepositoryImpl
import com.work.restaurant.data.source.remote.FitnessCenterDataImpl
import com.work.restaurant.network.model.FitnessCenterItemModel
import com.work.restaurant.view.search.contract.SearchRankContract

class SearchRankPresenter(private val searchRankView: SearchRankContract.View) :
    SearchRankContract.Presenter {
    override fun getFitnessList() {

        FitnessItemRepositoryImpl.getInstance(FitnessCenterDataImpl.getInstance())
            .getFitnessResult(object : FitnessItemRepositoryCallback {
                override fun onSuccess(fitnessList: List<FitnessCenterItemModel>) {
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