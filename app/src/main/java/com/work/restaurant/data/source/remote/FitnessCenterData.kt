package com.work.restaurant.data.source.remote

interface FitnessCenterData {

    fun getFitnessCenterData(
        callback: FitnessCenterDataCallback
    )
}