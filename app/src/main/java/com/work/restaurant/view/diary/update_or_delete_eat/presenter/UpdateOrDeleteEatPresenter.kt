package com.work.restaurant.view.diary.update_or_delete_eat.presenter

import android.util.Log
import com.work.restaurant.data.model.EatModel
import com.work.restaurant.data.repository.eat.EatRepository
import com.work.restaurant.data.repository.eat.EatRepositoryCallback

class UpdateOrDeleteEatPresenter(
    private val updateOrDeleteEatView: UpdateOrDeleteEatContract.View,
    private val eatRepository: EatRepository
) : UpdateOrDeleteEatContract.Presenter {

    override fun deleteEat(data: EatModel) {

        val toEatEntity = data.toEatEntity()

        eatRepository.deleteEat(toEatEntity, object : EatRepositoryCallback.DeleteEatCallback {
            override fun onSuccess() {
                updateOrDeleteEatView.showResult(2)
            }

            override fun onFailure() {
                updateOrDeleteEatView.showResult(0)
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
            object : EatRepositoryCallback.UpdateEatCallback {
                override fun onSuccess() {

                    updateOrDeleteEatView.showResult(1)
                }

                override fun onFailure() {
                    updateOrDeleteEatView.showResult(0)
                }
            })


    }
}