package com.work.restaurant.view.search.presenter

import com.work.restaurant.data.repository.search.FitnessItemRepositoryCallback
import com.work.restaurant.data.repository.search.FitnessItemRepositoryImpl
import com.work.restaurant.data.source.remote.FitnessCenterDataImpl
import com.work.restaurant.network.model.FitnessCenterItemModel
import com.work.restaurant.view.search.contract.SearchLookContract

class SearchLookPresenter(private val searchLookView: SearchLookContract.View) :
    SearchLookContract.Presenter {
    override fun backPage() {
        searchLookView.showBackPage()
    }

    override fun searchLook(searchItem: String) {

        if (searchItem.trim() == "") {
            searchLookView.showSearchNoFind()
        } else {

            FitnessItemRepositoryImpl.getInstance(FitnessCenterDataImpl.getInstance())
                .getFitnessResult(object : FitnessItemRepositoryCallback {
                    override fun onSuccess(fitnessList: List<FitnessCenterItemModel>) {

                        var count = 0
                        val _fitnessList = mutableListOf<FitnessCenterItemModel>()


                        fitnessList.forEach { fitnessCenterItemModel ->
                            if (fitnessCenterItemModel.fitnessCenterName.contains(searchItem)) {
                                _fitnessList.add(fitnessCenterItemModel)
                                count++
                            }
                        }


                        if (count == 0) {
                            searchLookView.showSearchNoFind()
                        } else {
                            searchLookView.showSearchLook(_fitnessList)
                            count = 0

                        }

                    }

                    override fun onFailure(message: String) {
                        searchLookView.showSearchNoFind()
                    }
                })


        }


    }
}