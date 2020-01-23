package com.work.restaurant.view.calendar

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.work.restaurant.R
import com.work.restaurant.view.base.BaseFragment
import kotlinx.android.synthetic.main.calendar_main.*

class CalendarFragment : BaseFragment(R.layout.calendar_main),
    View.OnClickListener {


    override fun onClick(v: View?) {

        when (v?.id) {

        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        calender_view.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val msg = year.toString() + (month.toString() + 1) + dayOfMonth.toString()
            Toast.makeText(this.context, msg, Toast.LENGTH_SHORT).show()
            //날짜 터치시 알림표시로 나타내기
            //textView.text = "$msg"
        }


    }

    companion object {
        private const val TAG = "CalendarFragment"


    }

}
