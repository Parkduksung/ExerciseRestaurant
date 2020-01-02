package com.work.restaurant.data.repository.fitness

import com.work.restaurant.network.model.FitnessCenterItemResponse

interface FitnessItemRepositoryCallback {

    fun onSuccess(fitnessList: List<FitnessCenterItemResponse>)
    fun onFailure(message: String)

}