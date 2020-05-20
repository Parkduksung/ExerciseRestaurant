package com.work.restaurant.data.source.remote.question

interface QuestionRemoteDataSource {
    fun sendQuestion(
        question: String,
        callback: (isSuccess: Boolean) -> Unit
    )
}