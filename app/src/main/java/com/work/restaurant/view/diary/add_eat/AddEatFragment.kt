package com.work.restaurant.view.diary.add_eat

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.RadioGroup
import android.widget.TimePicker
import android.widget.Toast
import com.work.restaurant.Injection
import com.work.restaurant.R
import com.work.restaurant.data.model.DiaryModel
import com.work.restaurant.util.App
import com.work.restaurant.view.base.BaseDialogFragment
import com.work.restaurant.view.diary.add_eat.presenter.AddEatContract
import com.work.restaurant.view.diary.add_eat.presenter.AddEatPresenter
import com.work.restaurant.view.diary.main.DiaryFragment
import kotlinx.android.synthetic.main.diary_add_eat.*
import java.text.SimpleDateFormat
import java.util.*


class AddEatFragment : BaseDialogFragment(R.layout.diary_add_eat),
    View.OnClickListener, AddEatContract.View {


    private lateinit var presenter: AddEatPresenter

    override fun showAddSuccess() {

        targetFragment?.onActivityResult(
            targetRequestCode,
            Activity.RESULT_OK,
            null
        )
        dismiss()
        Toast.makeText(this.context, "저장되었습니다.", Toast.LENGTH_SHORT).show()

    }


    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.btn_add_eat_time -> {
                getTimePicker()
            }

            R.id.add_eat_cancel -> {
                dismiss()
            }

            R.id.add_eat_save -> {

                if (radioClick <= 1 && et_add_eat_memo.text.isNotBlank()) {
                    presenter.addEat(
                        tv_add_eat_today.text.toString(),
                        btn_add_eat_time.text.toString(),
                        radioClick,
                        et_add_eat_memo.text.toString()
                    )
                    radioClick = 2

                } else {
                    Toast.makeText(this.context, "저장할 수 없습니다.", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        presenter = AddEatPresenter(
            this,
            Injection.provideEatRepository()
        )
        btn_add_eat_time.setOnClickListener(this)
        add_eat_cancel.setOnClickListener(this)
        add_eat_save.setOnClickListener(this)


        val bundle = arguments
        val getDate = bundle?.getString(DATE).orEmpty()
        val getTime = bundle?.getString(TIME).orEmpty()
        val getType = bundle?.getString(TYPE).orEmpty()
        val getMemo = bundle?.getString(MEMO).orEmpty()

        Log.d("가져옴?", getDate)
        Log.d("가져옴?", getTime)
        Log.d("가져옴?", getType)
        Log.d("가져옴?", getMemo)

        if (getDate.isNotEmpty() && getTime.isNotEmpty() && getType.isNotEmpty() && getMemo.isNotEmpty()) {
            et_add_eat_memo.setText(getMemo)
            tv_add_eat_today.text = getDate
            btn_add_eat_time.text = getTime

            if (getType.toInt() == 0) {
                add_eat_radio_group.check(R.id.rb_meal)
            } else {
                add_eat_radio_group.check(R.id.rb_snack)
            }
        }


    }

    private fun getRadioClickNum(radioGroup: RadioGroup) {

        radioGroup.setOnCheckedChangeListener { _, checkedId ->

            when (checkedId) {
                R.id.rb_meal -> {
                    radioClick = 0
                }

                R.id.rb_snack -> {
                    radioClick = 1
                }
            }
        }
    }

    private fun init() {
        tv_add_eat_today.text =
            App.prefs.current_date
        btn_add_eat_time.text =
            getCurrentTime()
        getRadioClickNum(add_eat_radio_group)
    }

    fun t(data: DiaryModel) {

//        et_add_eat_memo.setText(data.memo)
//        tv_add_eat_today.text = data.date
//        btn_add_eat_time.text = data.time
//        add_eat_radio_group.check((data.type.toInt()))
        Log.d("여기찍힘", data.time)
        Log.d("여기찍힘", data.type)
        Log.d("여기찍힘", data.date)
        Log.d("여기찍힘", data.memo)

        Log.d("여기찍힘", DiaryFragment.c.toString())

        Log.d("여기찍힘", "hi~")
    }

    private fun getTimePicker() {
        val dialogView = View.inflate(context, R.layout.time_picker, null)

        val timePicker = dialogView.findViewById<TimePicker>(R.id.time_picker)

        val alertDialog =
            android.app.AlertDialog.Builder(
                ContextThemeWrapper(
                    activity,
                    R.style.Theme_AppCompat_Light_Dialog
                )
            )

        alertDialog.setView(dialogView)
            .setPositiveButton("변경") { _, _ ->

                if (timePicker.minute / 10 == 0) {
                    val changedTime =
                        "${getAmPm(timePicker.hour)} 0${timePicker.minute}분"
                    btn_add_eat_time.text = changedTime
                } else {
                    val changedTime =
                        "${getAmPm(timePicker.hour)} ${timePicker.minute}분"
                    btn_add_eat_time.text = changedTime
                }

            }
            .setNegativeButton("취소") { _, _ ->

            }
            .show()

    }

    private fun getAmPm(hour: Int): String {
        return if (hour > 12) {
            "오후 ${hour - 12}시"
        } else {
            "오전 ${hour}시"
        }

    }

    //3월1일  이거 좀 이상함..
    private fun getCurrentTime(): String {
        val currentTime = Calendar.getInstance().time
        val dateTextAll =
            SimpleDateFormat("yyyy-M-d-EE-a-h-mm", Locale.getDefault()).format(currentTime)
        val dateArray = dateTextAll.split("-")
        return getString(
            R.string.current_time,
            dateArray[4],
            dateArray[5],
            dateArray[6]
        )
    }


    companion object {
        const val TAG = "AddEatFragment"
        private var radioClick = 2


        const val MEMO = "memo"
        const val TIME = "time"
        const val TYPE = "type"
        const val DATE = "date"


        fun newInstance(
            memo: String,
            time: String,
            type: String,
            date: String
        ) = AddEatFragment().apply {
            arguments = Bundle().apply {
                putString(MEMO, memo)
                putString(TIME, time)
                putString(TYPE, type)
                putString(DATE, date)
            }
        }

    }

}
