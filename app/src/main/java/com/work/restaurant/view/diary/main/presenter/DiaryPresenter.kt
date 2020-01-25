package com.work.restaurant.view.diary.main.presenter

import com.work.restaurant.data.repository.eat.EatRepository
import com.work.restaurant.data.repository.eat.EatRepositoryCallback
import com.work.restaurant.network.model.EatResponse

class DiaryPresenter(
    private val diaryView: DiaryContract.View,
    private val eatRepository: EatRepository
) : DiaryContract.Presenter {


    override fun data() {

        eatRepository.getList(object : EatRepositoryCallback.GetAllList {

            override fun onSuccess(list: List<EatResponse>) {

                val getDateModel = list.map {
                    it.toDateModel()
                }
                diaryView.showData(getDateModel)
            }

            override fun onFailure(msg: String) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }
}


