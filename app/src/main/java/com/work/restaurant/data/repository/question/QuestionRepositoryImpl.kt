package com.work.restaurant.data.repository.question

import com.work.restaurant.data.source.remote.question.QuestionRemoteDataSource

class QuestionRepositoryImpl(
    private val questionRemoteDataSource: QuestionRemoteDataSource
) : QuestionRepository {

    override fun sendQuestion(question: String, callback: (isSuccess: Boolean) -> Unit) {
        questionRemoteDataSource.sendQuestion(question, callback)
    }

}