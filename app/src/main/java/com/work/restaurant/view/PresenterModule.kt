package com.work.restaurant.view

import com.work.restaurant.view.diary.main.presenter.DiaryContract
import com.work.restaurant.view.diary.main.presenter.DiaryPresenter
import org.koin.dsl.module

val presenterModule = module {
    factory<DiaryContract.Presenter> { (view: DiaryContract.View) ->
        DiaryPresenter(
            view,
            get(),
            get()
        )
    }
}