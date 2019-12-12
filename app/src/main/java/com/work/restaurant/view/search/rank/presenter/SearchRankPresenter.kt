package com.work.restaurant.view.search.rank.presenter

import android.util.Log
import com.work.restaurant.data.repository.search.FitnessItemRepositoryCallback
import com.work.restaurant.data.repository.search.FitnessItemRepositoryImpl
import com.work.restaurant.data.source.remote.FitnessCenterDataImpl
import com.work.restaurant.network.RetrofitInstance
import com.work.restaurant.network.model.FitnessCenterItemResponse
import com.work.restaurant.view.search.rank.presenter.SearchRankContract
import com.work.restaurant.view.search.fragment.SearchFragment

class SearchRankPresenter(private val searchRankView: SearchRankContract.View) :
    SearchRankContract.Presenter {
    override fun getFitnessList() {

        FitnessItemRepositoryImpl.getInstance(
            FitnessCenterDataImpl.getInstance(
                RetrofitInstance.getInstance(
                    SearchFragment.URL
                )
            )
        )
            .getFitnessResult(object : FitnessItemRepositoryCallback {
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