package com.work.restaurant.data.repository.question

interface QuestionRepositoryCallback {
    fun onSuccess(message: String)
    fun onFailure(message: String)
}