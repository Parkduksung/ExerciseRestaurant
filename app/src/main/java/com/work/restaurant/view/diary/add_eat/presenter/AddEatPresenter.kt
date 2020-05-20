package com.work.restaurant.view.diary.add_eat.presenter

import com.work.restaurant.data.repository.eat.EatRepository

class AddEatPresenter(
    private val addEatView: AddEatContract.View,
    private val eatRepository: EatRepository
) : AddEatContract.Presenter {
    override fun addEat(userId: String, date: String, time: String, type: Int, memo: String) {
        eatRepository.addEat(
            userId,
            date,
            time,
            type,
            memo,
            callback = { add ->
                if (add) addEatView.showAddSuccess()
            }
        )

    }

}