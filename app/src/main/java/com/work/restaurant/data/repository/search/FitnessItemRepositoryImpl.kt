package com.work.restaurant.data.repository.search

import com.work.restaurant.data.source.remote.FitnessCenterData
import com.work.restaurant.data.source.remote.FitnessCenterDataCallback
import com.work.restaurant.network.model.FitnessCenterItemModel

class FitnessItemRepositoryImpl private constructor(
    private val fitnessCenterData: FitnessCenterData
) : FitnessItemRepository {
    override fun getFitnessResult(callback: FitnessItemRepositoryCallback) {

        fitnessCenterData.getFitnessCenterData(object : FitnessCenterDataCallback {
            override fun onSuccess(fitnessList: List<FitnessCenterItemModel>) {
                callback.onSuccess(fitnessList)
            }

            override fun onFailure(message: String) {
                callback.onFailure(message)
            }
        })

    }

    companion object {

        private var instance: FitnessItemRepositoryImpl? = null
        fun getInstance(
            fitnessCenterData: FitnessCenterData
        ): FitnessItemRepositoryImpl =
            instance ?: FitnessItemRepositoryImpl(fitnessCenterData).also {
                instance = it
            }

    }

}