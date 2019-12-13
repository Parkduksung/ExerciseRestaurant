package com.work.restaurant.data.repository.fitness

import com.work.restaurant.data.source.remote.fitness.FitnessCenterRemoteDataSource
import com.work.restaurant.data.source.remote.fitness.FitnessCenterRemoteDataSourceCallback
import com.work.restaurant.ext.isConnectedToNetwork
import com.work.restaurant.network.model.FitnessCenterItemResponse
import com.work.restaurant.util.App

class FitnessItemRepositoryImpl private constructor(
    private val fitnessCenterRemoteDataSource: FitnessCenterRemoteDataSource
) : FitnessItemRepository {


    override fun getFitnessResult(callback: FitnessItemRepositoryCallback) {

        if (App.instance.context().isConnectedToNetwork()) {
            fitnessCenterRemoteDataSource.getFitnessCenterData(object :
                FitnessCenterRemoteDataSourceCallback {
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
            fitnessCenterRemoteDataSource: FitnessCenterRemoteDataSource
        ): FitnessItemRepositoryImpl =
            instance ?: FitnessItemRepositoryImpl(fitnessCenterRemoteDataSource).also {
                instance = it
            }

    }

}