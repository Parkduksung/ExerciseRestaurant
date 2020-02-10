package com.work.restaurant.data.repository.question

import com.work.restaurant.data.source.remote.question.QuestionRemoteDataSourceCallback
import com.work.restaurant.data.source.remote.question.QuestionRemoteDataSourceImpl

class QuestionRepositoryImpl(
    private val questionRemoteDataSourceImpl: QuestionRemoteDataSourceImpl
) : QuestionRepository {
    override fun sendQuestion(question: String, callback: QuestionRepositoryCallback) {
        questionRemoteDataSourceImpl.sendQuestion(
            question,
            object : QuestionRemoteDataSourceCallback {
                override fun onSuccess(message: String) {
                    callback.onSuccess(message)
                }

                override fun onFailure(message: String) {
                    callback.onFailure(message)
                }

            })
    }


    companion object {

        private var instance: QuestionRepositoryImpl? = null
        fun getInstance(
            questionRemoteDataSourceImpl: QuestionRemoteDataSourceImpl
        ): QuestionRepositoryImpl =
            instance ?: QuestionRepositoryImpl(questionRemoteDataSourceImpl).also {
                instance = it
            }


    }


}