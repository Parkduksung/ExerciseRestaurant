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
import com.work.restaurant.util.App
import com.work.restaurant.view.base.BaseFragment
import com.work.restaurant.view.calendar.decorator.EatDecorator
import com.work.restaurant.view.calendar.decorator.ExerciseDecorator
import com.work.restaurant.view.calendar.decorator.SaturdayDecorator
import com.work.restaurant.view.calendar.decorator.SundayDecorator
import com.work.restaurant.view.calendar.presenter.CalendarContract
import com.work.restaurant.view.calendar.presenter.CalendarPresenter
import com.work.restaurant.view.diary.main.adapter.DiaryAdapter
import kotlinx.android.synthetic.main.calendar_main.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashSet

class CalendarFragment : BaseFragment(R.layout.calendar_main),
    CalendarContract.View {

    private lateinit var toHashSetCalendarDayEat: HashSet<CalendarDay>
    private lateinit var toHashSetCalendarDayExercise: HashSet<CalendarDay>

    private lateinit var presenter: CalendarPresenter
    private val diaryAdapter: DiaryAdapter by lazy { DiaryAdapter() }

    private val eat = mutableSetOf<DiaryModel>()
    private val exercise = mutableSetOf<DiaryModel>()


    override fun showAllDayIncludeExerciseData(list: Set<String>) {

        toHashSetCalendarDayExercise = HashSet()

        list.forEach {

            val splitList = it.split(" ")
            if (splitList.size == 3) {
                val year = splitList[0].substring(0, splitList[0].length - 1).toInt()
                val month = splitList[1].substring(0, splitList[1].length - 1).toInt()
                val day = splitList[2].substring(0, splitList[2].length - 1).toInt()

                val toCalendarDay = CalendarDay.from(
                    year,
                    (month - 1),
                    day
                )
                toHashSetCalendarDayExercise.add(toCalendarDay)
            }
        }

        dotEat = true

        showDotAndWeekend()

    }

    override fun showAllDayIncludeEatData(list: Set<String>) {

        toHashSetCalendarDayEat = HashSet()

        list.forEach {

            val splitList = it.split(" ")

            if (splitList.size == 3) {
                val year = splitList[0].substring(0, splitList[0].length - 1).toInt()
                val month = splitList[1].substring(0, splitList[1].length - 1).toInt()
                val day = splitList[2].substring(0, splitList[2].length - 1).toInt()


                val toCalendarDay = CalendarDay.from(
                    year,
                    (month - 1),
                    day
                )
                toHashSetCalendarDayEat.add(toCalendarDay)
            }
        }

        dotExercise = true
        showDotAndWeekend()

    }


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


        presenter.getAllEatData()
        presenter.getAllExerciseData()

        initCalendar()

        clickDate(calender_view)


    }

    private fun initCalendar() {

        val lastDayOfMonth = Calendar.getInstance().getMaximum(Calendar.DAY_OF_MONTH)

        val getYearAndMonth =
            SimpleDateFormat("yyyy-M-d", Locale.getDefault()).format(Calendar.getInstance().time)

        val dateArray = getYearAndMonth.split("-")

        calender_view.selectedDate = CalendarDay.today()
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


        presenter.getDataOfTheDayEatData(App.prefs.current_date)
        presenter.getDataOfTheDayExerciseData(App.prefs.current_date)


    }

    private fun showDotAndWeekend() {

        if (dotEat && dotExercise) {

            val toHashSetCalendarDayCompound = HashSet<CalendarDay>()

            toHashSetCalendarDayCompound.addAll(toHashSetCalendarDayEat)
            toHashSetCalendarDayCompound.addAll(toHashSetCalendarDayExercise)

            calender_view.removeDecorators()

            calender_view.addDecorator(
                EatDecorator(
                    toHashSetCalendarDayEat
                )
            )
            calender_view.addDecorator(
                ExerciseDecorator(
                    toHashSetCalendarDayExercise
                )
            )
            calender_view.addDecorator(SaturdayDecorator())
            calender_view.addDecorator(SundayDecorator())

            dotExercise = false
            dotEat = false
        }
    }


    private fun clickDate(calendarView: MaterialCalendarView) {

        calendarView.setOnDateChangedListener { _, date, _ ->

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


    private fun showDayOfData() {
        if (workEat && workExercise) {

            val dayOfSet = mutableSetOf<DiaryModel>()
            dayOfSet.addAll(eat)
            dayOfSet.addAll(exercise)
            workEat = false
            workExercise = false

            if (dayOfSet.size == 0) {
                Toast.makeText(App.instance.context(), "저장된 기록이 없습니다.", Toast.LENGTH_SHORT).show()
            }


            recyclerview_calendar.run {
                diaryAdapter.clearListData()
                if (dayOfSet.size != 0)
                    diaryAdapter.addAllData(dayOfSet.toList().sortedBy { it.time })
            }
        }
    }


    override fun onResume() {
        super.onResume()

        presenter.getAllExerciseData()
        presenter.getAllEatData()
    }

    companion object {
        private const val TAG = "CalendarFragment"

        var workEat = false
        var workExercise = false


        var dotEat = false
        var dotExercise = false
    }

}
