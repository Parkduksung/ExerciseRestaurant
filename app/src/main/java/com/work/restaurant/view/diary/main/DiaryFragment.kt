package com.work.restaurant.view.diary.main

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.work.restaurant.Injection
import com.work.restaurant.R
import com.work.restaurant.data.model.DiaryModel
import com.work.restaurant.data.model.EatModel
import com.work.restaurant.data.model.ExerciseModel
import com.work.restaurant.util.App
import com.work.restaurant.view.adapter.AdapterDataListener
import com.work.restaurant.view.base.BaseFragment
import com.work.restaurant.view.diary.add_eat.AddEatFragment
import com.work.restaurant.view.diary.add_exercise.AddExerciseFragment
import com.work.restaurant.view.diary.main.adapter.DiaryAdapter
import com.work.restaurant.view.diary.main.presenter.DiaryContract
import com.work.restaurant.view.diary.main.presenter.DiaryPresenter
import kotlinx.android.synthetic.main.diary_main.*
import java.text.SimpleDateFormat
import java.util.*


class DiaryFragment : BaseFragment(R.layout.diary_main),
    View.OnClickListener,
    DiaryContract.View,
    AdapterDataListener.GetList {


    private lateinit var presenter: DiaryPresenter
    private val diaryAdapter: DiaryAdapter by lazy { DiaryAdapter() }
    private val diaryModel = mutableSetOf<DiaryModel>()


    override fun showResult(msg: Boolean) {
        Log.d("결과", msg.toString())
    }

    override fun getData(data: DiaryModel) {

        if (data.kind == 1) {

            val alertDialog =
                AlertDialog.Builder(
                    ContextThemeWrapper(
                        activity,
                        R.style.Theme_AppCompat_Light_Dialog
                    )
                )
            alertDialog.setTitle("삭제")
            alertDialog.setMessage("입력한 정보를 삭제하시겠습니까?")
            alertDialog.setPositiveButton(
                "삭제"
            ) { _, _ ->
                recyclerview_diary.run {
                    diaryAdapter.deleteDate(data)
                }
                presenter.deleteExercise(data)
                diaryModel.remove(data)
            }
            alertDialog.setNegativeButton(
                "취소"
            ) { _, _ -> }

            alertDialog.show()

        } else {

            val alertDialog =
                AlertDialog.Builder(
                    ContextThemeWrapper(
                        activity,
                        R.style.Theme_AppCompat_Light_Dialog
                    )
                )
            alertDialog.setTitle("삭제")
            alertDialog.setMessage("입력한 정보를 삭제하시겠습니까?")
            alertDialog.setPositiveButton(
                "삭제"
            ) { _, _ ->
                recyclerview_diary.run {
                    diaryAdapter.deleteDate(data)
                }
                presenter.deleteEat(data)
                diaryModel.remove(data)
            }
            alertDialog.setNegativeButton(
                "취소"
            ) { _, _ -> }
            alertDialog.show()
        }
    }


    override fun showExerciseData(data: List<ExerciseModel>) {
        val getDiaryModel = data.map {
            it.toDiaryModel()
        }

        diaryModel.addAll(getDiaryModel)


        recyclerview_diary.run {
            diaryAdapter.clearListData()
            diaryAdapter.addAllData(diaryModel.toList().sortedBy { it.time })
        }


    }


    override fun showEatData(data: List<EatModel>) {
        val getDiaryModel = data.map {
            it.toDiaryModel()
        }
        diaryModel.addAll(getDiaryModel)


        recyclerview_diary.run {
            diaryAdapter.clearListData()
            diaryAdapter.addAllData(diaryModel.toList().sortedBy { it.time })
        }

    }

    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.btn_add_eat -> {

                val addEatFragment = AddEatFragment()

                addEatFragment.setTargetFragment(
                    this,
                    REGISTER_EAT
                )
                requireFragmentManager().beginTransaction()
                    .replace(R.id.main_container, addEatFragment)
                    .addToBackStack(null)
                    .commit()
            }

            R.id.btn_add_exercise -> {

                val addExerciseFragment = AddExerciseFragment()
                addExerciseFragment.setTargetFragment(
                    this,
                    REGISTER_EXERCISE
                )
                requireFragmentManager().beginTransaction()
                    .replace(R.id.main_container, addExerciseFragment)
                    .addToBackStack(null)
                    .commit()
            }

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = DiaryPresenter(
            this,
            Injection.provideEatRepository(),
            Injection.provideExerciseRepository()
        )
        recyclerview_diary.run {
            this.adapter = diaryAdapter
            layoutManager = LinearLayoutManager(this.context)
        }

        load()

        btn_add_eat.setOnClickListener(this)
        btn_add_exercise.setOnClickListener(this)
        diaryAdapter.setItemClickListener(this)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REGISTER_EAT -> {
                if (resultCode == Activity.RESULT_OK) {
                    load()
                }
            }
            REGISTER_EXERCISE -> {
                if (resultCode == Activity.RESULT_OK) {
                    load()
                }
            }
        }
    }

    private fun load() {

        val dayOfTheWeek =
            SimpleDateFormat("EE", Locale.getDefault())
                .format(Calendar.getInstance().time)

        tv_today.text =
            getString(
                R.string.diary_main_date1,
                App.prefs.current_date,
                dayOfTheWeek
            )


        presenter.todayEatData(
            App.prefs.current_date
        )
        presenter.todayExerciseData(
            App.prefs.current_date
        )

    }

    companion object {
        private const val TAG = "DiaryFragment"
        private const val REGISTER_EAT = 1
        private const val REGISTER_EXERCISE = 2
    }

}
