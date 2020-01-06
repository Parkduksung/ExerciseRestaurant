package com.work.restaurant.view.fragment.search.itemdetails.presenter

import android.util.Log
import com.work.restaurant.data.repository.fitness.FitnessItemRepository
import com.work.restaurant.data.repository.fitness.FitnessItemRepositoryCallback
import com.work.restaurant.network.model.FitnessCenterItemResponse

class SearchItemDetailsPresenter(
    private val searchItemDetailsView: SearchItemDetailsContract.View,
    private val fitnessItemRepository: FitnessItemRepository
) :
    SearchItemDetailsContract.Presenter {
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