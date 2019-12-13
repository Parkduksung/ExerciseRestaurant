package com.work.restaurant.view.search.rank.presenter

import android.util.Log
import com.work.restaurant.data.repository.fitness.FitnessItemRepositoryCallback
import com.work.restaurant.data.repository.fitness.FitnessItemRepositoryImpl
import com.work.restaurant.data.source.remote.fitness.FitnessCenterRemoteDataSourceImpl
import com.work.restaurant.network.RetrofitInstance
import com.work.restaurant.network.model.FitnessCenterItemResponse
import com.work.restaurant.view.search.main.SearchFragment

class SearchRankPresenter(private val searchRankView: SearchRankContract.View) :
    SearchRankContract.Presenter {
    override fun getFitnessList() {

        FitnessItemRepositoryImpl.getInstance(
            FitnessCenterRemoteDataSourceImpl.getInstance(
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