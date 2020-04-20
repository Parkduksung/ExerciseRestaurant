package com.work.restaurant.view.calendar

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.CalendarMode
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.work.restaurant.Injection
import com.work.restaurant.R
import com.work.restaurant.data.model.DiaryModel
import com.work.restaurant.data.model.EatModel
import com.work.restaurant.data.model.ExerciseModel
import com.work.restaurant.util.App
import com.work.restaurant.util.AppExecutors
import com.work.restaurant.util.DateAndTime
import com.work.restaurant.view.base.BaseFragment
import com.work.restaurant.view.calendar.decorator.EatDecorator
import com.work.restaurant.view.calendar.decorator.ExerciseDecorator
import com.work.restaurant.view.calendar.decorator.SaturdayDecorator
import com.work.restaurant.view.calendar.decorator.SundayDecorator
import com.work.restaurant.view.calendar.presenter.CalendarContract
import com.work.restaurant.view.calendar.presenter.CalendarPresenter
import com.work.restaurant.view.diary.main.adapter.DiaryDetailsAdapter
import kotlinx.android.synthetic.main.calendar_main.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashSet

class CalendarFragment : BaseFragment(R.layout.calendar_main),
    CalendarContract.View {

    private lateinit var toHashSetCalendarDayEat: HashSet<CalendarDay>
    private lateinit var toHashSetCalendarDayExercise: HashSet<CalendarDay>

    private lateinit var presenter: CalendarPresenter
    private val diaryDetailsAdapter: DiaryDetailsAdapter by lazy { DiaryDetailsAdapter() }

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
            this.adapter = diaryDetailsAdapter
            layoutManager = LinearLayoutManager(this.context)
        }

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




        showExplain()

        renewDot()
    }

    private fun showDotAndWeekend() {

        if (dotEat && dotExercise) {
            calender_view.removeDecorators()

            AppExecutors().diskIO.execute {
                val decoratorList = mutableListOf<DayViewDecorator>()

                val eatDecorator = EatDecorator(toHashSetCalendarDayEat)
                val exerciseDecorator = ExerciseDecorator(toHashSetCalendarDayExercise)
                val saturdayDecorator = SaturdayDecorator()
                val sundayDecorator = SundayDecorator()

                decoratorList.apply {
                    add(eatDecorator)
                    add(exerciseDecorator)
                    add(saturdayDecorator)
                    add(sundayDecorator)
                }

                AppExecutors().mainThread.execute {
                    calender_view.addDecorators(decoratorList)
                }
            }
            dotExercise = false
            dotEat = false
        }
    }

    fun renewDot() {
        if (App.prefs.login_state && App.prefs.login_state_id.isNotEmpty()) {

            loginStateId = App.prefs.login_state_id
            loginState = App.prefs.login_state

            tv_calendar_main_context.text =
                getString(R.string.et_calendar_main_context_ok_login_state)
            presenter.run {
                getAllEatData(loginStateId)
                getAllExerciseData(loginStateId)
                getDataOfTheDayEatData(loginStateId, DateAndTime.currentDate())
                getDataOfTheDayExerciseData(loginStateId, DateAndTime.currentDate())
            }
            calender_view.selectedDate = CalendarDay.today()
            toggleMessage = true

        } else {
            loginStateId = App.prefs.login_state_id
            loginState = App.prefs.login_state
            tv_calendar_main_context.text =
                getString(R.string.et_calendar_main_context_no_login_state)
            calender_view.removeDecorators()
            toggleExplain = true
            showExplain()
        }
    }

    private fun clickDate(calendarView: MaterialCalendarView) {

        calendarView.setOnDateChangedListener { _, date, _ ->
            if (App.prefs.login_state && App.prefs.login_state_id.isNotEmpty()) {
                toggleMessage = false
                val msg = getString(
                    R.string.current_date,
                    date.year.toString(),
                    (date.month + 1).toString(),
                    date.day.toString()
                )

                presenter.run {
                    getDataOfTheDayEatData(loginStateId, msg)
                    getDataOfTheDayExerciseData(loginStateId, msg)
                }

            } else {
                Toast.makeText(
                    this.context,
                    getString(R.string.calendar_login_state_no),
                    Toast.LENGTH_SHORT
                ).show()
            }
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
                tv_calendar_main_context.text =
                    getString(R.string.et_calendar_main_context_ok_login_state)
                if (!toggleExplain) {
                    toggleExplain = true
                    showExplain()
                }
                if (!toggleMessage) {
//                    Toast.makeText(App.instance.context(), "저장된 기록이 없습니다.", Toast.LENGTH_SHORT)
//                        .show()
                }
            } else {
                recyclerview_calendar.run {
                    diaryDetailsAdapter.clearListData()
                    if (dayOfSet.size != 0)
                        diaryDetailsAdapter.addAllData(dayOfSet.toList().sortedBy { it.time })
                }
                toggleExplain = false
                showExplain()
            }
        }
    }

    private fun showExplain() {
        tv_calendar_main_context.isVisible = toggleExplain
        recyclerview_calendar.isVisible = !toggleExplain
    }


    companion object {

        private const val TAG = "CalendarFragment"


        private var loginState = false
        private var loginStateId = ""

        private var workEat = false
        private var workExercise = false

        private var toggleExplain = true
        private var toggleMessage = false

        private var dotEat = false
        private var dotExercise = false

    }

}