package com.work.restaurant.data.source.remote

import com.work.restaurant.network.model.FitnessCenterItemModel

interface FitnessCenterDataCallback {

    fun onSuccess(fitnessList: List<FitnessCenterItemModel>)

    fun onFailure(message: String)
}