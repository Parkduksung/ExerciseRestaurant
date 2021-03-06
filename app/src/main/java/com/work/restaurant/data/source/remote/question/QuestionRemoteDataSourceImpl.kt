package com.work.restaurant.data.source.remote.question

import com.work.restaurant.network.api.QuestionApi
import com.work.restaurant.network.model.ResultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class QuestionRemoteDataSourceImpl(private val questionApi: QuestionApi) :
    QuestionRemoteDataSource {

    override fun sendQuestion(question: String, callback: (isSuccess: Boolean) -> Unit) {
        questionApi.register(question).enqueue(object : Callback<ResultResponse> {
            override fun onFailure(call: Call<ResultResponse>?, t: Throwable?) {
                callback(false)
            }

            override fun onResponse(
                call: Call<ResultResponse>?,
                response: Response<ResultResponse>?
            ) {

                val result = response?.body()?.result

                if (result != null) {
                    if (result) {
                        callback(true)
                    } else {
                        callback(false)
                    }
                }
            }

        })
    }

}