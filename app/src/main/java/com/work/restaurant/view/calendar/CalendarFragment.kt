package com.work.restaurant.view.calendar

import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.work.restaurant.Injection
import com.work.restaurant.R
import com.work.restaurant.data.model.DiaryModel
import com.work.restaurant.data.model.EatModel
import com.work.restaurant.data.model.ExerciseModel
import com.work.restaurant.view.base.BaseFragment
import com.work.restaurant.view.calendar.presenter.CalendarContract
import com.work.restaurant.view.calendar.presenter.CalendarPresenter
import com.work.restaurant.view.diary.main.adapter.DiaryAdapter
import kotlinx.android.synthetic.main.calendar_main.*

class CalendarFragment : BaseFragment(R.layout.calendar_main),
    CalendarContract.View {


    private lateinit var presenter: CalendarPresenter
    private val diaryAdapter: DiaryAdapter by lazy { DiaryAdapter() }

    private val eat = mutableSetOf<DiaryModel>()
    private val exercise = mutableSetOf<DiaryModel>()


    override fun showEatData(data: List<EatModel>) {

        eat.clear()
        val toDateModel = data.map {
            it.toDiaryModel()
        }
        eat.addAll(toDateModel)
        workEat = true

        showDayOfData()
    }

    override fun showExerciseData(data: List<ExerciseModel>) {

        exercise.clear()

        val toDateModel = data.map {
            it.toDiaryModel()
        }

        exercise.addAll(toDateModel)
        workExercise = true

        showDayOfData()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = CalendarPresenter(
            this,
            Injection.provideEatRepository(),
            Injection.provideExerciseRepository()
        )

        recyclerview_calendar.run {
            this.adapter = diaryAdapter
            layoutManager = LinearLayoutManager(this.context)
        }


        clickDate(calender_view)
    }

    private fun clickDate(calendarView: CalendarView) {

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->

            val msg = getString(
                R.string.current_date,
                year.toString(),
                (month + 1).toString(),
                dayOfMonth.toString()
            )
            Toast.makeText(this.context, msg, Toast.LENGTH_SHORT).show()

            presenter.getDataOfTheDayEatData(msg)
            presenter.getDataOfTheDayExerciseData(msg)
        }
    }

    private fun showDayOfData() {
        if (workEat && workExercise) {

            val dayOfSet = mutableSetOf<DiaryModel>()
            dayOfSet.addAll(eat)
            dayOfSet.addAll(exercise)
            workEat = false
            workExercise = false

            this.activity?.runOnUiThread {
                recyclerview_calendar.run {
                    diaryAdapter.clearListData()
                    diaryAdapter.addAllData(dayOfSet.toList().sortedBy { it.time })
                }
            }
        }
    }

    companion object {
        private const val TAG = "CalendarFragment"

        var workEat = false
        var workExercise = false
    }

}
