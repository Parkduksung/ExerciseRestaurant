package com.work.restaurant.view.search.presenter

import com.work.restaurant.data.repository.search.FitnessItemRepositoryCallback
import com.work.restaurant.data.repository.search.FitnessItemRepositoryImpl
import com.work.restaurant.data.source.remote.FitnessCenterDataImpl
import com.work.restaurant.network.RetrofitInstance
import com.work.restaurant.network.model.FitnessCenterItemResponse
import com.work.restaurant.view.search.contract.SearchLookContract
import com.work.restaurant.view.search.fragment.SearchFragment

class SearchLookPresenter(private val searchLookView: SearchLookContract.View) :
    SearchLookContract.Presenter {
    override fun backPage() {
        searchLookView.showBackPage()
    }

    override fun searchLook(searchItem: String) {

        if (searchItem.trim() == "") {
            searchLookView.showSearchNoFind()
        } else {

            FitnessItemRepositoryImpl.getInstance(
                FitnessCenterDataImpl.getInstance(
                    RetrofitInstance.getInstance(
                        SearchFragment.URL
                    )
                )
            )
                .getFitnessResult(object : FitnessItemRepositoryCallback {
                    override fun onSuccess(fitnessList: List<FitnessCenterItemResponse>) {

                        var count = 0
                        val _fitnessList = mutableListOf<FitnessCenterItemResponse>()


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