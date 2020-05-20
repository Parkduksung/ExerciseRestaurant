package com.work.restaurant.view.mypage.question.presenter

interface MyPageQuestionContract {

    interface View {

        fun showResult(resultState: Boolean)

        fun showProgressState(state: Boolean)
    }

    interface Presenter {

        fun sendQuestion(question: String)
    }
}