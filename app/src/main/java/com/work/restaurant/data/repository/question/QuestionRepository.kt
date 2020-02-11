package com.work.restaurant.data.repository.question

interface QuestionRepository {

    fun sendQuestion(
        question: String,
        callback: QuestionRepositoryCallback
    )

}