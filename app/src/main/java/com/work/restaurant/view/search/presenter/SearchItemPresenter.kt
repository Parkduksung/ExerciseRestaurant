package com.work.restaurant.view.search.presenter

import android.util.Log
import com.work.restaurant.data.repository.search.FitnessItemRepositoryCallback
import com.work.restaurant.data.repository.search.FitnessItemRepositoryImpl
import com.work.restaurant.data.source.remote.FitnessCenterDataImpl
import com.work.restaurant.network.RetrofitInstance
import com.work.restaurant.network.model.FitnessCenterItemResponse
import com.work.restaurant.view.search.contract.SearchItemContract
import com.work.restaurant.view.search.fragment.SearchFragment.Companion.URL

class SearchItemPresenter(private val searchItemView: SearchItemContract.View) :
    SearchItemContract.Presenter {
    override fun call(callNum: String) {
        searchItemView.showCall(callNum)
    }

    override fun itemInfoDetail(searchItem: String) {

        FitnessItemRepositoryImpl.getInstance(
            FitnessCenterDataImpl.getInstance(
                RetrofitInstance.getInstance(
                    URL
                )
            )
        )
            .getFitnessResult(object : FitnessItemRepositoryCallback {
                override fun onSuccess(fitnessList: List<FitnessCenterItemResponse>) {


                    fitnessList.forEach { fitnessCenterItemModel ->
                        if (fitnessCenterItemModel.fitnessCenterName == searchItem) {

                            searchItemView.showItemInfoDetail(fitnessCenterItemModel)
                        }
                    }

                }

                override fun onFailure(message: String) {
                    Log.d("error", message)
                }
            })


    }


}