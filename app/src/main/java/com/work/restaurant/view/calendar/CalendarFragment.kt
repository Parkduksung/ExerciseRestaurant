package com.work.restaurant.view.calendar

import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.work.restaurant.Injection
import com.work.restaurant.R
import com.work.restaurant.data.model.DiaryModel
import com.work.restaurant.view.base.BaseFragment
import com.work.restaurant.view.calendar.presenter.CalendarContract
import com.work.restaurant.view.calendar.presenter.CalendarPresenter
import com.work.restaurant.view.diary.main.adapter.DiaryAdapter
import kotlinx.android.synthetic.main.calendar_main.*

class CalendarFragment : BaseFragment(R.layout.calendar_main),
    View.OnClickListener, CalendarContract.View {
    override fun showDataOfTheDay(list: List<DiaryModel>) {
        dayOfList.addAll(list)
    }

    private lateinit var presenter: CalendarPresenter
    private lateinit var diaryAdapter: DiaryAdapter

    private val dayOfList = mutableSetOf<DiaryModel>()


    override fun onClick(v: View?) {

        when (v?.id) {

        }
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

            //            dayOfList.clear()

            val msg = getString(
                R.string.current_date,
                year.toString(),
                (month.toString() + 1),
                dayOfMonth.toString()
            )
            Toast.makeText(this.context, msg, Toast.LENGTH_SHORT).show()

            presenter.getDataOfTheDay(msg)


            recyclerview_calendar.run {
                diaryAdapter.clearListData()
                diaryAdapter.addAllData(dayOfList.toList().sortedBy { it.time })
            }

        }

    }


    companion object {
        private const val TAG = "CalendarFragment"


    }

}
