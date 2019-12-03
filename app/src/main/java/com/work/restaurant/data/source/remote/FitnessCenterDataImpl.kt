package com.work.restaurant.data.source.remote

import com.work.restaurant.network.api.FitnessCenterApi
import com.work.restaurant.network.model.FitnessCenterItemModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FitnessCenterDataImpl : FitnessCenterData {
    override fun getFitnessCenterData(
        callback: FitnessCenterDataCallback
    ) {


        val retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        val fitnessApi = retrofit.create(FitnessCenterApi::class.java)


        fitnessApi.fitnessCenterAllItem().enqueue(object : Callback<List<FitnessCenterItemModel>> {

            override fun onFailure(call: Call<List<FitnessCenterItemModel>>?, t: Throwable?) {
                callback.onFailure("$t")
            }

            override fun onResponse(
                call: Call<List<FitnessCenterItemModel>>?,
                response: Response<List<FitnessCenterItemModel>>?
            ) {

                response?.body()?.let {
                    callback.onSuccess(it)
                }

            }
        })

    }

    companion object {

        private const val URL = "https://duksung12.cafe24.com"

        private var instance: FitnessCenterDataImpl? = null

        fun getInstance(): FitnessCenterDataImpl =
            instance ?: instance ?: FitnessCenterDataImpl().also {
                instance = it
            }


    }

}