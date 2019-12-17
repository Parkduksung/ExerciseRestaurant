package com.work.restaurant.view.search

import com.work.restaurant.data.repository.fitness.FitnessItemRepositoryCallback
import com.work.restaurant.data.repository.fitness.FitnessItemRepositoryImpl
import com.work.restaurant.data.source.remote.fitness.FitnessCenterRemoteDataSourceImpl
import com.work.restaurant.network.RetrofitInstance
import com.work.restaurant.network.model.FitnessCenterItemResponse
import com.work.restaurant.view.search.main.SearchFragment

class SearchLookForPresenter(private val searchLookForView: SearchLookForContract.View) :
    SearchLookForContract.Presenter {
    override fun backPage() {
        searchLookForView.showBackPage()
    }

    override fun searchLook(searchItem: String) {

        if (searchItem.trim() == "") {
            searchLookForView.showSearchNoFind()
        } else {

            FitnessItemRepositoryImpl.getInstance(
                FitnessCenterRemoteDataSourceImpl.getInstance(
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
                            searchLookForView.showSearchNoFind()
                        } else {
                            searchLookForView.showSearchLook(_fitnessList)
                            count = 0

                        }

                    }

                    override fun onFailure(message: String) {
                        searchLookForView.showSearchNoFind()
                    }
                })


        }


    }
}