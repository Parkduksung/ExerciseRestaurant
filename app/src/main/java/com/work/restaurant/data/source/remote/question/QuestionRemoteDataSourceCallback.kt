package com.work.restaurant.data.source.remote.question

interface QuestionRemoteDataSourceCallback {
    fun onSuccess(message: String)
    fun onFailure(message: String)
}