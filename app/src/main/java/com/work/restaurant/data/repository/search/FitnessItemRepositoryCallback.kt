package com.work.restaurant.data.repository.search

import com.work.restaurant.network.model.FitnessCenterItemModel

interface FitnessItemRepositoryCallback {

    fun onSuccess(fitnessList: List<FitnessCenterItemModel>)
    fun onFailure(message: String)

}