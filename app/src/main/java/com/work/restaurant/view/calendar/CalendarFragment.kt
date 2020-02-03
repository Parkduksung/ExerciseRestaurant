package com.work.restaurant.view.calendar

import android.os.Bundle
import android.util.Log
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
    private lateinit var diaryAdapter: DiaryAdapter


    private val eat = mutableSetOf<DiaryModel>()
    private val exercise = mutableSetOf<DiaryModel>()


    override fun showEatData(data: List<EatModel>) {

        eat.clear()

        val toDateModel = data.map {
            it.toDiaryModel()
        }

        eat.addAll(toDateModel)
        a = true
    }

    override fun showExerciseData(data: List<ExerciseModel>) {

        exercise.clear()

        val toDateModel = data.map {
            it.toDiaryModel()
        }

        exercise.addAll(toDateModel)
        b = true
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        diaryAdapter = DiaryAdapter()
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

            val dayOfList = mutableSetOf<DiaryModel>()

            val msg = getString(
                R.string.current_date,
                year.toString(),
                (month + 1).toString(),
                dayOfMonth.toString()
            )
            Toast.makeText(this.context, msg, Toast.LENGTH_SHORT).show()

            presenter.getDataOfTheDayEatData(msg)
            presenter.getDataOfTheDayExerciseData(msg)


            if (a && b) {
                dayOfList.addAll(eat)
                dayOfList.addAll(exercise)
                a = false
                b = false

                Log.d("결콰콰코카1", dayOfList.size.toString())

                this.activity?.runOnUiThread {
                    recyclerview_calendar.run {
                        diaryAdapter.clearListData()
                        diaryAdapter.addAllData(dayOfList.toList().sortedBy { it.time })
                    }
                }
            }

        }
    }

    companion object {
        private const val TAG = "CalendarFragment"

        var a = false
        var b = false
    }

}
