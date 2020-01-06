package com.work.restaurant.view.activity.search

import com.work.restaurant.data.repository.fitness.FitnessItemRepository
import com.work.restaurant.data.repository.fitness.FitnessItemRepositoryCallback
import com.work.restaurant.network.model.FitnessCenterItemResponse

class SearchLookForPresenter(
    private val searchLookForView: SearchLookForContract.View,
    private val fitnessItemRepository: FitnessItemRepository
) :
    SearchLookForContract.Presenter {
    override fun backPage() {
        searchLookForView.showBackPage()
    }

    override fun searchLook(searchItem: String) {

        if (searchItem.trim() == "") {
            searchLookForView.showSearchNoFind()
        } else {

            fitnessItemRepository.getFitnessResult(object : FitnessItemRepositoryCallback {
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