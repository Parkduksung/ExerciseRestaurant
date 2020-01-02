package com.work.restaurant.data.source.remote.fitness

import com.work.restaurant.network.model.FitnessCenterItemResponse

interface FitnessCenterRemoteDataSourceCallback {

    fun onSuccess(fitnessList: List<FitnessCenterItemResponse>)

    fun onFailure(message: String)
}