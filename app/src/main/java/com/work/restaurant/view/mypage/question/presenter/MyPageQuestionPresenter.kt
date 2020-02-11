package com.work.restaurant.view.mypage.question.presenter

import com.work.restaurant.data.repository.question.QuestionRepository
import com.work.restaurant.data.repository.question.QuestionRepositoryCallback

class MyPageQuestionPresenter(
    private val questionView: MyPageQuestionContract.View,
    private val questionRepository: QuestionRepository
) :
    MyPageQuestionContract.Presenter {
    override fun sendQuestion(question: String) {

        questionRepository.sendQuestion(question, object : QuestionRepositoryCallback {
            override fun onSuccess(message: String) {
                questionView.showResult(message)
            }

            override fun onFailure(message: String) {
                questionView.showResult(message)
            }
        })

    }
}