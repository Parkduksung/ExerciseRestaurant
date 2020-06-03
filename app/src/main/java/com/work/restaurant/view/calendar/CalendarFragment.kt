package com.work.restaurant.view.calendar

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.CalendarMode
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.work.restaurant.R
import com.work.restaurant.data.model.DiaryModel
import com.work.restaurant.data.model.EatModel
import com.work.restaurant.data.model.ExerciseModel
import com.work.restaurant.databinding.CalendarMainBinding
import com.work.restaurant.ext.showToast
import com.work.restaurant.util.AppExecutors
import com.work.restaurant.util.DateAndTime
import com.work.restaurant.util.RelateLogin
import com.work.restaurant.view.base.BaseFragment
import com.work.restaurant.view.calendar.decorator.EatDecorator
import com.work.restaurant.view.calendar.decorator.ExerciseDecorator
import com.work.restaurant.view.calendar.decorator.SaturdayDecorator
import com.work.restaurant.view.calendar.decorator.SundayDecorator
import com.work.restaurant.view.calendar.presenter.CalendarContract
import com.work.restaurant.view.diary.main.adapter.DiaryDetailsAdapter
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashSet

class CalendarFragment :
    BaseFragment<CalendarMainBinding>(CalendarMainBinding::bind, R.layout.calendar_main),
    CalendarContract.View {

    private lateinit var toHashSetCalendarDayEat: HashSet<CalendarDay>
    private lateinit var toHashSetCalendarDayExercise: HashSet<CalendarDay>

    private lateinit var presenter: CalendarContract.Presenter
    private val diaryDetailsAdapter: DiaryDetailsAdapter by lazy { DiaryDetailsAdapter() }

    private val eat = mutableSetOf<DiaryModel>()
    private val exercise = mutableSetOf<DiaryModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = get { parametersOf(this) }

        binding.rvCalendar.run {
            this.adapter = diaryDetailsAdapter
            layoutManager = LinearLayoutManager(this.context)
        }

        startCalendar()
        clickDate(binding.calenderView)
    }

    private fun startCalendar() {

        val getYearAndMonth =
            SimpleDateFormat(YEAR_MONTH, Locale.getDefault()).format(Calendar.getInstance().time)

        val dateArray =
            getYearAndMonth.split(SPLIT_YEAR_MONTH_TEXT)

        binding.calenderView.apply {
            selectedDate = CalendarDay.today()
            state().edit()
                .isCacheCalendarPositionEnabled(true)
                .setMaximumDate(
                    CalendarDay.from(
                        dateArray[0].toInt(),
                        (dateArray[1].toInt() - 1),
                        DateAndTime.lastDayOfThisMonth()
                    )
                )
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit()
        }


        showExplain()

        renewDot()
    }

    private fun clickDate(calendarView: MaterialCalendarView) {

        calendarView.setOnDateChangedListener { _, date, _ ->
            if (RelateLogin.loginState()) {
                toggleMessage = false
                val msg =
                    getString(
                        R.string.common_date,
                        date.year.toString(),
                        (date.month + 1).toString(),
                        date.day.toString()
                    )

                presenter.run {
                    getDataOfTheDayEatData(loginStateId, msg)
                    getDataOfTheDayExerciseData(loginStateId, msg)
                }

            } else {
                showToast(getString(R.string.calendar_login_state_no))
            }
        }
    }

    override fun showAllDayIncludeEatData(list: Set<String>) {
        toHashSetCalendarDayEat = HashSet()
        addConvertDate(list, SORT_EAT)
        dotExercise = true
        showDotAndWeekend()
    }

    override fun showAllDayIncludeExerciseData(list: Set<String>) {
        toHashSetCalendarDayExercise = HashSet()
        addConvertDate(list, SORT_EXERCISE)
        dotEat = true
        showDotAndWeekend()

    }

    private fun addConvertDate(list: Set<String>, sort: Int) {
        list.forEach {
            val splitList = it.split(SPLIT_TEXT)
            if (splitList.size == 3) {
                val year = splitList[0].substring(0, splitList[0].length - 1).toInt()
                val month = splitList[1].substring(0, splitList[1].length - 1).toInt()
                val day = splitList[2].substring(0, splitList[2].length - 1).toInt()

                val toCalendarDay = CalendarDay.from(
                    year,
                    (month - 1),
                    day
                )

                when (sort) {
                    SORT_EAT -> {
                        toHashSetCalendarDayEat.add(toCalendarDay)
                    }
                    SORT_EXERCISE -> {
                        toHashSetCalendarDayExercise.add(toCalendarDay)
                    }
                }
            }
        }
    }

    override fun showEatData(data: List<EatModel>) {

        eat.clear()
        val toDateModel =
            data.map {
                it.toDiaryModel()
            }
        eat.addAll(toDateModel)
        workEat = true

        showDayOfData()
    }

    override fun showExerciseData(data: List<ExerciseModel>) {

        exercise.clear()

        val toDateModel =
            data.map {
                it.toDiaryModel()
            }

        exercise.addAll(toDateModel)
        workExercise = true

        showDayOfData()
    }

    private fun showDotAndWeekend() {

        if (dotEat && dotExercise) {
            binding.calenderView.removeDecorators()

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
                    binding.calenderView.addDecorators(decoratorList)
                }
            }
            dotExercise = false
            dotEat = false
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
                binding.tvCalendarMainContext.text =
                    getString(R.string.common_ok_login_state_but_not_have_data)
                if (!toggleExplain) {
                    toggleExplain = true
                    showExplain()
                }
            } else {
                binding.rvCalendar.run {
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
        binding.tvCalendarMainContext.isVisible = toggleExplain
        binding.rvCalendar.isVisible = !toggleExplain
    }

    fun renewDot() {
        if (RelateLogin.loginState()) {

            loginStateId = RelateLogin.getLoginId()
            loginState = RelateLogin.getLoginState()

            binding.tvCalendarMainContext.text =
                getString(R.string.common_ok_login_state_but_not_have_data)

            presenter.run {
                getAllEatData(loginStateId)
                getAllExerciseData(loginStateId)
                getDataOfTheDayEatData(loginStateId, DateAndTime.currentDate())
                getDataOfTheDayExerciseData(loginStateId, DateAndTime.currentDate())
            }

            binding.calenderView.selectedDate = CalendarDay.today()
            toggleMessage = true

        } else {
            loginStateId = RelateLogin.getLoginId()
            loginState = RelateLogin.getLoginState()
            binding.tvCalendarMainContext.text =
                getString(R.string.calendar_no_login_state)
            binding.calenderView.removeDecorators()
            toggleExplain = true
            showExplain()
        }
    }

    companion object {

        private const val TAG = "CalendarFragment"

        private const val SPLIT_TEXT = " "

        private const val YEAR_MONTH = "yyyy-M"

        private const val SPLIT_YEAR_MONTH_TEXT = "-"

        private var loginState = false
        private var loginStateId = ""

        private var workEat = false
        private var workExercise = false

        private var toggleExplain = true
        private var toggleMessage = false

        private var dotEat = false
        private var dotExercise = false

        private const val SORT_EAT = 0
        private const val SORT_EXERCISE = 1

    }

}