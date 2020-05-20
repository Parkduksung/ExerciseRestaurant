package com.work.restaurant.view.mypage.question.presenter

import com.work.restaurant.data.repository.question.QuestionRepository

class MyPageQuestionPresenter(
    private val questionView: MyPageQuestionContract.View,
    private val questionRepository: QuestionRepository
) :
    MyPageQuestionContract.Presenter {
    override fun sendQuestion(question: String) {

        questionRepository.sendQuestion(
            question,
            callback = { isSuccess ->
                if (isSuccess) {
                    questionView.showResult(true)
                } else {
                    questionView.showResult(false)
                }
            })
    }
}