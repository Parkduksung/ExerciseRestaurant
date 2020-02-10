package com.work.restaurant.view.mypage.question.presenter

interface MyPageQuestionContract {

    interface View {

        fun showResult(message: String)
    }

    interface Presenter {

        fun sendQuestion(question: String)
    }
}