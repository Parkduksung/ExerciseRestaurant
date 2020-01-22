package com.work.restaurant.view.calendar

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import com.work.restaurant.R
import com.work.restaurant.util.App
import com.work.restaurant.view.base.BaseFragment
import kotlinx.android.synthetic.main.calendar_main.*
import java.text.SimpleDateFormat
import java.util.*

class CalendarFragment : BaseFragment(R.layout.calendar_main),
    View.OnClickListener {


    override fun onClick(v: View?) {

        when (v?.id) {

        }
    }

    var button_date: Button? = null
    var textview_date: TextView? = null
    var cal = Calendar.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.calendar_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        textview_date = this.text_view_date_1
        button_date = this.button_date_1

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            date_Picker.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
                Toast.makeText(
                    this.context,
                    "$year ${monthOfYear + 1} $dayOfMonth",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }






        calender?.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val msg: String = year.toString() + month.toString() + dayOfMonth.toString()
            Toast.makeText(this.context, msg, Toast.LENGTH_SHORT).show() //날짜 터치시 알림표시로 나타내기
//textView.text = "$msg"
        }





        textview_date!!.text = "--/--/----"

        // create an OnDateSetListener
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(
                view: DatePicker, year: Int, monthOfYear: Int,
                dayOfMonth: Int
            ) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }

        // when you click on the button, show DatePickerDialog that is set with OnDateSetListener
        button_date!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(
                    App.instance.context(),
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }

        })


    }

    private fun updateDateInView() {
        val myFormat = "MM/dd/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        textview_date!!.text = sdf.format(cal.getTime())
    }


    companion object {
        private const val TAG = "CalendarFragment"


    }

}
