package com.work.restaurant.view.diary.update_or_delete_eat.presenter

import com.work.restaurant.data.model.EatModel

interface UpdateOrDeleteEatContract {

    interface View {
        fun showResult(sort: Int)
    }

    interface Presenter {

        fun deleteEat(data: EatModel)

        fun updateEat(time: String, type: Int, memo: String, data: EatModel)

    }
}