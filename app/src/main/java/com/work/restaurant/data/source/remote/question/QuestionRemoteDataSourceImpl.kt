package com.work.restaurant.data.source.remote.question

import com.work.restaurant.network.api.QuestionApi
import com.work.restaurant.network.model.ResultResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class QuestionRemoteDataSourceImpl(private val questionApi: QuestionApi) :
    QuestionRemoteDataSource {
    override fun sendQuestion(question: String, callback: QuestionRemoteDataSourceCallback) {
        questionApi.register(question).enqueue(object : Callback<ResultResponse> {
            override fun onFailure(call: Call<ResultResponse>?, t: Throwable?) {
                callback.onFailure("${t?.message}")
            }

            override fun onResponse(
                call: Call<ResultResponse>?,
                response: Response<ResultResponse>?
            ) {

                val result = response?.body()?.result

                if (result != null) {
                    if (result) {
                        callback.onSuccess("success")
                    } else {
                        callback.onFailure("error")
                    }
                }
            }

        })
    }


    companion object {

        private var instance: QuestionRemoteDataSourceImpl? = null

        fun getInstance(questionApi: QuestionApi): QuestionRemoteDataSourceImpl =
            instance ?: QuestionRemoteDataSourceImpl(questionApi).also {
                instance = it
            }

    }
}