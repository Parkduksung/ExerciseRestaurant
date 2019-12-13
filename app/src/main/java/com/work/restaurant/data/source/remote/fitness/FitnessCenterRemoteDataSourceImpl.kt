package com.work.restaurant.data.source.remote.fitness

import com.work.restaurant.network.api.FitnessCenterApi
import com.work.restaurant.network.model.FitnessCenterItemResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FitnessCenterRemoteDataSourceImpl private constructor(private val fitnessCenterApi: FitnessCenterApi) :
    FitnessCenterRemoteDataSource {
    override fun getFitnessCenterData(
        callbackRemoteSource: FitnessCenterRemoteDataSourceCallback
    ) {


        fitnessCenterApi.fitnessCenterAllItem()
            .enqueue(object : Callback<List<FitnessCenterItemResponse>> {

                override fun onFailure(
                    call: Call<List<FitnessCenterItemResponse>>?,
                    t: Throwable?
                ) {
                    callbackRemoteSource.onFailure("${t?.message}")
                }

                override fun onResponse(
                    call: Call<List<FitnessCenterItemResponse>>?,
                    response: Response<List<FitnessCenterItemResponse>>?
                ) {

                    response?.body()?.let {
                        callbackRemoteSource.onSuccess(it)
                    }

                }
            })

    }

    companion object {

        private var instance: FitnessCenterRemoteDataSourceImpl? = null

        fun getInstance(fitnessCenterApi: FitnessCenterApi): FitnessCenterRemoteDataSourceImpl =
            instance
                ?: instance
                ?: FitnessCenterRemoteDataSourceImpl(
                    fitnessCenterApi
                ).also {
                instance = it
            }


    }

}