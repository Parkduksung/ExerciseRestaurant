package com.work.restaurant.view.calendar

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.CalendarMode
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
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
import java.text.SimpleDateFormat
import java.util.*

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


        initCalendar()

        clickDate(calender_view)

        monthChange(calender_view)

    }

    private fun initCalendar() {

        val lastDayOfMonth = Calendar.getInstance().getMaximum(Calendar.DAY_OF_MONTH)

        val getYearAndMonth =
            SimpleDateFormat("yyyy-M", Locale.getDefault()).format(Calendar.getInstance().time)

        val dateArray = getYearAndMonth.split("-")

        CalendarDay.from(
            dateArray[0].toInt(),
            dateArray[1].toInt(),
            lastDayOfMonth
        )

        calender_view.state().edit()
            .isCacheCalendarPositionEnabled(true)
            .setMaximumDate(
                CalendarDay.from(
                    dateArray[0].toInt(),
                    (dateArray[1].toInt() - 1),
                    lastDayOfMonth
                )
            )
            .setCalendarDisplayMode(CalendarMode.MONTHS)
            .commit()

    }


    private fun clickDate(calendarView: MaterialCalendarView) {

        calendarView.setOnDateChangedListener { widget, date, selected ->

            val msg = getString(
                R.string.current_date,
                date.year.toString(),
                (date.month + 1).toString(),
                date.day.toString()
            )
            Toast.makeText(this.context, msg, Toast.LENGTH_SHORT).show()

            presenter.getDataOfTheDayEatData(msg)
            presenter.getDataOfTheDayExerciseData(msg)

        }


    }

    private fun monthChange(calendarView: MaterialCalendarView) {

        calendarView.setOnMonthChangedListener { widget, date ->

        }

    }

    private fun showDayOfData() {
        if (workEat && workExercise) {

            val dayOfSet = mutableSetOf<DiaryModel>()
            dayOfSet.addAll(eat)
            dayOfSet.addAll(exercise)
            workEat = false
            workExercise = false

            recyclerview_calendar.run {
                diaryAdapter.clearListData()
                diaryAdapter.addAllData(dayOfSet.toList().sortedBy { it.time })
            }
        }
    }

    companion object {
        private const val TAG = "CalendarFragment"

        var workEat = false
        var workExercise = false
    }

}
