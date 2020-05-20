package com.work.restaurant.view.diary.update_or_delete_eat.presenter

import com.work.restaurant.data.model.EatModel
import com.work.restaurant.data.repository.eat.EatRepository

class UpdateOrDeleteEatPresenter(
    private val updateOrDeleteEatView: UpdateOrDeleteEatContract.View,
    private val eatRepository: EatRepository
) : UpdateOrDeleteEatContract.Presenter {

    override fun deleteEat(data: EatModel) {

        val toEatEntity = data.toEatEntity()

        eatRepository.deleteEat(
            toEatEntity,
            callback = { delete ->
                if (delete) {
                    updateOrDeleteEatView.showResult(SUCCESS_DELETE)
                } else {
                    updateOrDeleteEatView.showResult(FAIL_DELETE)
                }
            })

    }

    override fun updateEat(time: String, type: Int, memo: String, data: EatModel) {

        val toEatEntity = data.toEatEntity()

        eatRepository.updateEat(
            time,
            type,
            memo,
            toEatEntity,
            callback = { update ->
                if (update) {
                    updateOrDeleteEatView.showResult(SUCCESS_UPDATE)
                } else {
                    updateOrDeleteEatView.showResult(FAIL_UPDATE)
                }
            })
    }

    companion object {

        const val FAIL_UPDATE = 0
        const val FAIL_DELETE = 1
        const val SUCCESS_UPDATE = 2
        const val SUCCESS_DELETE = 3

    }
}