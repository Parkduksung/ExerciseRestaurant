package com.work.restaurant.data.source.remote.fitness

interface FitnessCenterRemoteDataSource {

    fun getFitnessCenterData(callbackRemoteSource: FitnessCenterRemoteDataSourceCallback)
}