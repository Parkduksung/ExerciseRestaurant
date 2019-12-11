package com.work.restaurant.data.repository.search

import com.work.restaurant.data.source.remote.FitnessCenterData
import com.work.restaurant.data.source.remote.FitnessCenterDataCallback
import com.work.restaurant.ext.isConnectedToNetwork
import com.work.restaurant.network.model.FitnessCenterItemResponse
import com.work.restaurant.util.App

class FitnessItemRepositoryImpl private constructor(
    private val fitnessCenterData: FitnessCenterData
) : FitnessItemRepository {


    override fun getFitnessResult(callback: FitnessItemRepositoryCallback) {

        if (App.instance.context().isConnectedToNetwork()) {
            fitnessCenterData.getFitnessCenterData(object : FitnessCenterDataCallback {
                override fun onSuccess(fitnessList: List<FitnessCenterItemResponse>) {
                    callback.onSuccess(fitnessList)
                }

                override fun onFailure(message: String) {
                    callback.onFailure(message)
                }
            })
        }
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