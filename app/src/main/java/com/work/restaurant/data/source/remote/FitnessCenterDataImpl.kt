package com.work.restaurant.data.source.remote

import com.work.restaurant.network.api.FitnessCenterApi
import com.work.restaurant.network.model.FitnessCenterItemResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FitnessCenterDataImpl private constructor(private val fitnessCenterApi: FitnessCenterApi) :
    FitnessCenterData {
    override fun getFitnessCenterData(
        callback: FitnessCenterDataCallback
    ) {


        fitnessCenterApi.fitnessCenterAllItem()
            .enqueue(object : Callback<List<FitnessCenterItemResponse>> {

                override fun onFailure(
                    call: Call<List<FitnessCenterItemResponse>>?,
                    t: Throwable?
                ) {
                    callback.onFailure("$t")
                }

                override fun onResponse(
                    call: Call<List<FitnessCenterItemResponse>>?,
                    response: Response<List<FitnessCenterItemResponse>>?
                ) {

                    response?.body()?.let {
                        callback.onSuccess(it)
                    }

                }
            })

    }

    companion object {

        private var instance: FitnessCenterDataImpl? = null

        fun getInstance(fitnessCenterApi: FitnessCenterApi): FitnessCenterDataImpl =
            instance ?: instance ?: FitnessCenterDataImpl(fitnessCenterApi).also {
                instance = it
            }


    }

}