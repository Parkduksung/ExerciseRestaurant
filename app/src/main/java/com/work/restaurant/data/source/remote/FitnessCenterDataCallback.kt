package com.work.restaurant.data.source.remote

import com.work.restaurant.network.model.FitnessCenterItemResponse

interface FitnessCenterDataCallback {

    fun onSuccess(fitnessList: List<FitnessCenterItemResponse>)

    fun onFailure(message: String)
}