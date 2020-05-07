package com.work.restaurant.view

import com.work.restaurant.view.diary.main.presenter.DiaryContract
import com.work.restaurant.view.diary.main.presenter.DiaryPresenter

class InjectPresenter {

    private val injection = com.work.restaurant.Injection

    fun getDiaryPresenter(view: DiaryContract.View): DiaryPresenter =
        DiaryPresenter(
            view,
            injection.provideEatRepository(),
            injection.provideExerciseRepository()
        )
}