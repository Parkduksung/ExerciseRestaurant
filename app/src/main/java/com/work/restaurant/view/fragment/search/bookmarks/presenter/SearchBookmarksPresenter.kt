package com.work.restaurant.view.fragment.search.bookmarks.presenter

import android.util.Log
import com.work.restaurant.data.repository.fitness.FitnessItemRepository
import com.work.restaurant.data.repository.fitness.FitnessItemRepositoryCallback
import com.work.restaurant.network.model.FitnessCenterItemResponse

class SearchBookmarksPresenter(
    private val searchBookmarksView: SearchBookmarksContract.View,
    private val fitnessItemRepository: FitnessItemRepository
) :
    SearchBookmarksContract.Presenter {
    override fun getBookmarksList() {

        fitnessItemRepository.getFitnessResult(object : FitnessItemRepositoryCallback {
            override fun onSuccess(fitnessList: List<FitnessCenterItemResponse>) {
                searchBookmarksView.showBookmarksList(fitnessList)
            }

            override fun onFailure(message: String) {
                Log.d("error", message)
            }
        })

    }

}