package com.work.restaurant.view.diary.main.presenter

import com.work.restaurant.data.repository.eat.EatRepository
import com.work.restaurant.data.repository.eat.EatRepositoryCallback
import com.work.restaurant.data.repository.exercise.ExerciseRepository
import com.work.restaurant.data.repository.exercise.ExerciseRepositoryCallback
import com.work.restaurant.network.model.EatResponse
import com.work.restaurant.network.room.entity.EatEntity
import com.work.restaurant.network.room.entity.ExerciseEntity

class DiaryPresenter(
    private val diaryView: DiaryContract.View,
    private val eatRepository: EatRepository,
    private val exerciseRepository: ExerciseRepository
) : DiaryContract.Presenter {
    override fun todayExerciseData(today: String) {

        exerciseRepository.getDataOfTheDay(
            today,
            object : ExerciseRepositoryCallback.GetDataOfTheDay {
                override fun onSuccess(list: List<ExerciseEntity>) {


                    val getDataOfTheDayList = list.map {
                        it.toExerciseModel()
                    }

                    diaryView.showExerciseData(getDataOfTheDayList.sortedBy { it.time })

                }

                override fun onFailure(msg: String) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            })
    }


    override fun todayEatData(today: String) {

        eatRepository.getDataOfTheDay(today, object : EatRepositoryCallback.GetDataOfTheDay {
            override fun onSuccess(list: List<EatEntity>) {
                val getDataOfTheDayList = list.map {
                    it.toEatModel()
                }

                diaryView.showEatData(getDataOfTheDayList.sortedBy { it.time })

            }

            override fun onFailure(msg: String) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })


    }

    override fun exerciseData() {


        exerciseRepository.getList(object : ExerciseRepositoryCallback.GetAllList {
            override fun onSuccess(list: List<ExerciseEntity>) {


                val getExerciseModel = list.map {
                    it.toExerciseModel()
                }


            }


            override fun onFailure(msg: String) {

            }
        })

    }

    override fun data() {


        eatRepository.getList(object : EatRepositoryCallback.GetAllList {

            override fun onSuccess(list: List<EatResponse>) {

                val getEatModel = list.map {
                    it.toEatModel()
                }

                diaryView.showEatData(getEatModel)
            }

            override fun onFailure(msg: String) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

            }

        })

    }
}


