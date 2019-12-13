package com.work.restaurant.view.search.itemdetails.presenter

import android.util.Log
import com.work.restaurant.data.repository.fitness.FitnessItemRepositoryCallback
import com.work.restaurant.data.repository.fitness.FitnessItemRepositoryImpl
import com.work.restaurant.data.source.remote.fitness.FitnessCenterRemoteDataSourceImpl
import com.work.restaurant.network.RetrofitInstance
import com.work.restaurant.network.model.FitnessCenterItemResponse
import com.work.restaurant.view.search.main.SearchFragment.Companion.URL

class SearchItemDetailsPresenter(private val searchItemDetailsView: SearchItemDetailsContract.View) :
    SearchItemDetailsContract.Presenter {
    override fun call(callNum: String) {
        searchItemDetailsView.showCall(callNum)
    }

    override fun itemInfoDetail(searchItem: String) {

        FitnessItemRepositoryImpl.getInstance(
            FitnessCenterRemoteDataSourceImpl.getInstance(
                RetrofitInstance.getInstance(
                    URL
                )
            )
        )
            .getFitnessResult(object : FitnessItemRepositoryCallback {
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