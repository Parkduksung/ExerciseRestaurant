package com.work.restaurant.view.diary.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.work.restaurant.Injection
import com.work.restaurant.R
import com.work.restaurant.data.model.DiaryModel
import com.work.restaurant.data.model.EatModel
import com.work.restaurant.data.model.ExerciseModel
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
    AdapterDataListener {
    override fun showExerciseData(data: List<ExerciseModel>) {
        val getDiaryModel = data.map {
            it.toDiaryModel()
        }
        diaryModel.addAll(getDiaryModel)
    }

    override fun getData(data: String) {

        Toast.makeText(this.context, data, Toast.LENGTH_SHORT).show()

    }

    override fun showEatData(data: List<EatModel>) {

        val getDiaryModel = data.map {
            it.toDiaryModel()
        }

        diaryModel.addAll(getDiaryModel)

    }


    private lateinit var presenter: DiaryPresenter
    private lateinit var diaryAdapter: DiaryAdapter
    private var diaryModel = mutableListOf<DiaryModel>()

    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.btn_add_eat -> {

                requireFragmentManager().beginTransaction()
                    .replace(R.id.main_container, AddEatFragment())
                    .addToBackStack(null)
                    .commit()
            }

            R.id.btn_add_exercise -> {
                requireFragmentManager().beginTransaction()
                    .replace(R.id.main_container, AddExerciseFragment())
                    .addToBackStack(null)
                    .commit()
            }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.diary_main, container, false).also {
            diaryAdapter = DiaryAdapter()
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        presenter = DiaryPresenter(
            this,
            Injection.provideEatRepository(),
            Injection.provideExerciseRepository()
        )

        init()


        btn_add_eat.setOnClickListener(this)
        btn_add_exercise.setOnClickListener(this)
        diaryAdapter.setItemClickListener(this)


    }


    private fun init() {

        val currentTime = Calendar.getInstance().time

        val dateTextAll =
            SimpleDateFormat("yyyy-MM-dd-EE", Locale.getDefault()).format(currentTime)

        val dateArray = dateTextAll.split("-")

        tv_today.text =
            getString(
                R.string.diary_main_date,
                dateArray[0],
                dateArray[1],
                dateArray[2],
                dateArray[3]
            )
        presenter.todayEatData(
            getString(
                R.string.current_date,
                dateArray[0],
                dateArray[1],
                dateArray[2]
            )
        )

        presenter.todayExerciseData(
            getString(
                R.string.current_date,
                dateArray[0],
                dateArray[1],
                dateArray[2]
            )
        )

        recyclerview_diary.run {
            this.adapter = diaryAdapter
            diaryAdapter.clearListData()
            diaryAdapter.addAllData(diaryModel.distinct())
            layoutManager = LinearLayoutManager(this.context)
        }

    }

    companion object {
        private const val TAG = "DiaryFragment"

    }


    override fun onResume() {
        super.onResume()
        init()

    }

}
